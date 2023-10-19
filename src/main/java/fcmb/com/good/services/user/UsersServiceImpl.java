package fcmb.com.good.services.user;

import fcmb.com.good.common.UserConstant;
import fcmb.com.good.model.dto.response.ApiResponse;
import fcmb.com.good.model.dto.response.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.othersRequest.AuthRequest;
import fcmb.com.good.model.dto.request.userRequest.UsersRequest;
import fcmb.com.good.model.dto.request.userRequest.changeUserPasswordRequest;
import fcmb.com.good.model.dto.response.othersResponse.AuthResponse;
import fcmb.com.good.model.dto.response.userResponse.UsersResponse;
import fcmb.com.good.model.entity.activityLog.ActivityLog;
import fcmb.com.good.model.entity.user.UserCategory;
import fcmb.com.good.model.entity.user.Users;
import fcmb.com.good.repo.activityLog.ActivityLogRepository;
import fcmb.com.good.repo.user.UsersCategoryRepository;
import fcmb.com.good.repo.user.UsersRepository;
import fcmb.com.good.services.others.FileStorageService;
import fcmb.com.good.services.others.JwtAuthenticationServiceImpl;
import fcmb.com.good.utills.EmailUtils;
import fcmb.com.good.security.JwtUtil;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final UsersCategoryRepository usersCategoryRepository;
    private final EmailUtils emailUtils;
    private final JwtAuthenticationServiceImpl jwtAuthenticationService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageService fileStorageService;
    private final ActivityLogRepository activityLogRepository;

    String username;


    @Override
    /**
     * @Finding the list of Users
     * @Validate if the List of users is empty otherwise return record not found
     * @return the list of users and a Success Message*
     * * */
    public ApiResponse<List<UsersResponse>> getListOfUsers(int page, int size) {
            List<Users> userList = usersRepository.findAll(PageRequest.of(page, size)).toList();
            if (userList.isEmpty())
                throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

            return new ApiResponse(AppStatus.SUCCESS.label, Mapper.convertList(userList, UsersResponse.class));
        }



    /**
     * @validating userOptional by uuid*
     * @Validate if the List of user is empty otherwise return record not found
     * @return userOptional* *
     * * */
    public Users validateUser(UUID uuid) {
        Optional<Users> userOptional = usersRepository.findByUuid(uuid);
        if (userOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return userOptional.get();
    }


    /**
     * @Validating existingUsersOptional by email
     * @Validate if the List of existingUsersOptional is empty otherwise return Duplicate Record*
     * * */
    private void validateDuplicationUsers(String email){
        Optional<Users> existingUsersOptional = usersRepository.findByEmailId(email);
        if(existingUsersOptional.isPresent())
            throw new RecordNotFoundException("Email Already Exist");

    }


    /**
     * @return
     * @Validating existingUsersOptional by username
     * @Validate if the List of existingUsersOptional is empty otherwise return Duplicate Record*
     * *
     */
    private Users validateUsersByUseraname(String username){
        Optional<Users> existingUsersOptional = usersRepository.findUsersByUsername2(username);
        if(existingUsersOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return existingUsersOptional.get();
    }


    @Override
    /**
     * @Validate that no duplicate users is allowed*
     * @Validate that user exists otherwise return record not found*
     * Create user definition and save
     * @return success message
     * * */
    public ApiResponse<String> addUsers(UsersRequest request) throws IOException {

        validateDuplicationUsers(request.getEmail());

        UserCategory existingUserCategory = usersCategoryRepository.findByName(request.getUserCategory())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

            Users user = new Users();

            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setAddress(request.getAddress());
            user.setCountry(request.getCountry());
            user.setCity(request.getCity());
            user.setGender(request.getGender());
            user.setPassword(passwordEncoder.encode("test"));
            user.setPhone(request.getPhone());
            user.setUsername(request.getUsername());
            user.setUserCategory(existingUserCategory);
            user.setUsersCategory(existingUserCategory.getName());
            user.setRoles(UserConstant.DEFAULT_ROLE);

            usersRepository.save(user);

        ActivityLog activityLog = new ActivityLog();
        activityLog.setName("addUser");
        activityLog.setCategory("add");
        activityLog.setDescription("this is a add log");
        activityLog.setPerformedBy(user.getName());
        activityLog.setPerformedDate(LocalDateTime.now());

        activityLogRepository.save(activityLog);

            return new ApiResponse( "Record Added successfully", AppStatus.SUCCESS.label,
                    HttpStatus.OK.value());
        }


    @Override
    /**
     * @Finding the list of all userOptional by uuid*
     * @Validate if the List of userOptional is empty otherwise return record not found
     * Create the user definition and get the user Optional
     * @return the list of users and a Success Message* *
     * * */
    public ApiResponse<UsersResponse> getUsersById(UUID userId) {
            Optional<Users> userOptional = usersRepository.findByUuid(userId);
            if (userOptional.isEmpty())
                throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);


            Users appUser = userOptional.get();

            return new ApiResponse<UsersResponse>(AppStatus.SUCCESS.label,
                    Mapper.convertObject(appUser, UsersResponse.class));
        }


    @Override
    /**
     * @validating userOptional by uuid
     * @Validate if the List of user is empty otherwise return record not found
     * Create the user definition and update
     * @return a Success Message
     * * */
    public ApiResponse<String> updateUsers(UUID userUuid, UsersRequest request ) {

            Users user = validateUser(userUuid);

            if (request.getName() != null) {
                user.setName(request.getName());
            }
            if (request.getEmail() != null) {
                user.setEmail(request.getEmail());
            }
            if (request.getAddress() != null) {
                user.setAddress(request.getAddress());
            }
            if (request.getCountry() != null) {
                user.setCountry(request.getCountry());
            }
            if (request.getCity() != null) {
                user.setCity(request.getCity());
            }
            if (request.getGender() != null) {
                user.setGender(request.getGender());
            }
            if (request.getPhone() != null) {
                user.setPhone(request.getPhone());
            }
            if (request.getUsername() != null) {
                user.setUsername(request.getUsername());
            }
            if (request.getDesignation() != null) {
            user.setDesignation(request.getDesignation());
            }

            usersRepository.save(user);

        ActivityLog activityLog = new ActivityLog();
        activityLog.setName("updateUser");
        activityLog.setCategory("update");
        activityLog.setDescription("this is a user update log");
        activityLog.setPerformedBy(user.getName());
        activityLog.setPerformedDate(LocalDateTime.now());

        activityLogRepository.save(activityLog);

            return new ApiResponse<>(AppStatus.SUCCESS.label, "Record Updated Successfully");

        }

    @Override
    public ApiResponse<String> updateUsersPhoto(UUID userUuid, MultipartFile photoFile) {

        Users user = validateUser(userUuid);

        if (photoFile != null && !photoFile.isEmpty()) {
            String fileName = fileStorageService.storeFile(photoFile);
            user.setPhoto(fileName);
        }

        usersRepository.save(user);

        ActivityLog activityLog = new ActivityLog();
        activityLog.setName("photo");
        activityLog.setCategory("update");
        activityLog.setDescription("this is a photo update log");
        activityLog.setPerformedBy(user.getName());
        activityLog.setPerformedDate(LocalDateTime.now());

        activityLogRepository.save(activityLog);

        return new ApiResponse<>(AppStatus.SUCCESS.label, "Record Updated Successfully");

    }


    @Override
    /**
     * @validating user by uuid
     * @Validate if user is empty otherwise return record not found
     * @Delete user
     * @return a Success Message
     * * */
    public ApiResponse<String> deleteUsers(UUID userId) {
            Users user = validateUser(userId);
            usersRepository.delete(user);

        ActivityLog activityLog = new ActivityLog();
        activityLog.setName("deleteUser");
        activityLog.setCategory("delete");
        activityLog.setDescription("this is a delete log for user");
        activityLog.setPerformedBy(user.getName());
        activityLog.setPerformedDate(LocalDateTime.now());

        activityLogRepository.save(activityLog);

            return new ApiResponse(AppStatus.SUCCESS.label, "Record Deleted successfully");
        }


    @Override
    /**
     * @Validating existingUser by Email
     * @Validate user password and change password
     * @Save the new password and return a Success Message
     */
    public ApiResponse<String> resetUsersPassword(String email, changeUserPasswordRequest request) {
            Users users = usersRepository.findByEmail(email);

            if (users.getPassword().equals(request.getOldPassword())) {
                users.setPassword(request.getNewPassword());

                usersRepository.save(users);

                return new ApiResponse("Password Changed successfully", AppStatus.SUCCESS.label,
                        HttpStatus.OK.value());
            }
            return new ApiResponse("Incorrect Old Password", AppStatus.FAILED.label,
                    HttpStatus.BAD_REQUEST.value());
        }


    @Override
    /**
     * @Validating existingUser by Email
     * @Getiing email and password of user and sending it to User`s Email
     * @Return a Success Message
     */
    public ApiResponse<String> forgotUsersPassword(String email) throws MessagingException {

        Users users = usersRepository.findByEmail(email);

        emailUtils.forgotMail(users.getEmail(), "Your Booking Credentials by Hotel Management System", users.getPassword());

        return new ApiResponse("Check your email for credentials", AppStatus.SUCCESS.label,
                HttpStatus.OK.value());

    }


    @Override
    /**
     * @Validating and finding existingUser by Email
     * @Validate user email and password
     * @Return a Success Message if email and password is correct
     * @Return a Failed Message if email and password is Incorrect
     */
    public ApiResponse<AuthResponse> loginUsers(AuthRequest request) {
        Users users = usersRepository.findByUsername(request.getUsername());

        if (users.getUsername().equals(request.getUsername()) &&
                users.getPassword().equals(request.getPassword())) {

            return new ApiResponse<AuthResponse>(AppStatus.SUCCESS.label,
                    Mapper.convertObject(users, AuthResponse.class));
        }

        return new ApiResponse("Incorrect Email or Password", AppStatus.FAILED.label,
                HttpStatus.BAD_REQUEST.value());
    }



    @Override
    public ApiResponse<UsersResponse> getUsersByUsername(String username) {

    Optional<Users> existingUsersOption = usersRepository.findUsersByUsername(username);
    if(existingUsersOption.isEmpty())
        throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

    Users users = existingUsersOption.get();

    return  new ApiResponse<UsersResponse>(AppStatus.SUCCESS.label,
            Mapper.convertObject(users, UsersResponse.class));


    }

    @Override
    public ApiResponse<UsersResponse> getUsersByEmail(String email) {

        Optional<Users> existingUsersOption = usersRepository.findUsersByEmail(email);
        if(existingUsersOption.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        Users users = existingUsersOption.get();

        return  new ApiResponse<UsersResponse>(AppStatus.SUCCESS.label,
                Mapper.convertObject(users, UsersResponse.class));


    }

    private List<String> getRolesByLoggedInUser(Principal principal) {
        String roles = getLoggedInUser(principal).getRoles();
        List<String> assignRoles = Arrays.stream(roles.split(",")).collect(Collectors.toList());
        if (assignRoles.contains("ROLE_ADMIN")) {
            return Arrays.stream(UserConstant.ADMIN_ACCESS).collect(Collectors.toList());
        }
        if (assignRoles.contains("ROLE_MODERATOR")) {
            return Arrays.stream(UserConstant.MODERATOR_ACCESS).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private Users getLoggedInUser(Principal principal) {
        return usersRepository.findUsersByUsername(principal.getName()).get();
    }



}
