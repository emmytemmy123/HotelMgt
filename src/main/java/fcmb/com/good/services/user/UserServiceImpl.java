package fcmb.com.good.services.user;


import fcmb.com.good.common.UserConstant;
import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.filter.JwtFilter;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.othersRequest.AuthRequest;
import fcmb.com.good.model.dto.request.userRequest.UserRequest;
import fcmb.com.good.model.dto.request.userRequest.changeUserPasswordRequest;
import fcmb.com.good.model.dto.request.userRequest.loginUserRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.userResponse.UserResponse;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.repo.user.UserRepository;
import fcmb.com.good.utills.EmailUtils;
import fcmb.com.good.utills.JwtUtil;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl  implements UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final JwtFilter jwtFilter;
    private final EmailUtils emailUtils;



    @Override
    /**
     * @Authenticating username and password
     * @Validating if username and password is Authenticated otherwise return record not found
     * Generate token to access the Api
     */
    public String authenticate(AuthRequest authRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));

        if (!authentication.isAuthenticated())
            throw new UsernameNotFoundException("invalid user request !");

        return jwtUtil.generateToken(authRequest.getUserName());

    }


    @Override
    /**
     * @Finding the list of Users
     * @Validate if the List of users is empty otherwise return record not found
     * @return the list of users and a Success Message*
     * * */
    public ApiResponse<List<UserResponse>> getListOfUsers(int page, int size) {
            List<AppUser> userList = userRepository.findAll(PageRequest.of(page, size)).toList();
            if (userList.isEmpty())
                throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

            return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    Mapper.convertList(userList, UserResponse.class));
        }


    /**
     * Set and get the users parameters
     */
    private AppUser getUserFromRequest(UserRequest request) {
        AppUser user = new AppUser();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setAddress(request.getAddress());
        user.setCountry(request.getCountry());
        user.setCity(request.getCity());
        user.setGender(request.getGender());
        user.setPassword(request.getPassword());
        user.setPhone(request.getPhone());
        user.setUsername(request.getUsername());
        user.setPhoto(request.getPhoto());

        user.setRoles(UserConstant.DEFAULT_ROLE);//USER
//        String encryptedPwd = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encryptedPwd);

        return user;
    }



    @Override
    /**
     * @Validate that no duplicate users is allowed*
     * @Validate that user exists otherwise return record not found*
     * Create user definition and save
     * @return success message
     * * */
    public ApiResponse<String> addUsers(UserRequest request) {
            Optional<AppUser> user = validateUserByEmailId(request.getEmail());

            if (!user.isEmpty()) {
                return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
                        "Email Already Exist");
            }

        userRepository.save(getUserFromRequest(request));

            return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    "Record Added successfully");
        }


    @Override
    /**
     * @Finding the list of all userOptional by uuid*
     * @Validate if the List of userOptional is empty otherwise return record not found
     * Create the user definition and get the user Optional
     * @return the list of users and a Success Message* *
     * * */
    public ApiResponse<UserResponse> getUsersById( UUID userId) {
//        if (jwtFilter.isAdmin()) {
            Optional<AppUser> userOptional = userRepository.findByUuid(userId);
            if (userOptional.isEmpty())
                throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

            AppUser appUser = userOptional.get();
            return new ApiResponse<UserResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    Mapper.convertObject(appUser, UserResponse.class));
        }
//        return new ApiResponse(AppStatus.REJECT.label, HttpStatus.EXPECTATION_FAILED.value(),
//                "You are not Authorized");
//    }

    /**
     * @validating userOptional by uuid*
     * @Validate if the List of user is empty otherwise return record not found
     * @return userOptional* *
     * * */
    private AppUser validateUser(UUID uuid) {
        Optional<AppUser> userOptional = userRepository.findByUuid(uuid);
        if (userOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return userOptional.get();
    }

    /**
     * @Validating existingUserOptional by Email
     * @Validate if existingUserOptional is empty otherwise return Duplicate Record
     * return existingUserOptional
     * * */
    private Optional<AppUser> validateUserByEmailId(String email) {
        Optional<AppUser> existingUserOptional = userRepository.findByEmailId(email);
        return existingUserOptional;
    }


//    private AppUser postedByUuid(String postedBy) {
//        AppUser user = validateUser(UUID.fromString(postedBy));
//        AppUser users = new AppUser();
//        users.setPostedBy(String.valueOf(users));
//        return user;
//    }


    @Override
    /**
     * @validating userOptional by uuid
     * @Validate if the List of user is empty otherwise return record not found
     * Create the user definition and update
     * @return a Success Message
     * * */
    public ApiResponse<String> updateUser(UUID userId, UserRequest request) {

            AppUser user = validateUser(userId);

        if (request.getName() != null) {
            user.setName(request.getName());
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

        if (request.getPhoto() != null) {
            user.setPhoto(request.getPhoto());
        }



        userRepository.save(user);
            return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    "Record Updated Successfully");
        }


    @Override
    /**
     * @validating user by uuid
     * @Validate if user is empty otherwise return record not found
     * @Delete user
     * @return a Success Message
     * * */
    public ApiResponse<String> deleteUser(UUID userId) {
//        if (jwtFilter.isAdmin()) {
            AppUser user = validateUser(userId);
            userRepository.delete(user);
            return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    "Record Deleted successfully");
        }
//        return new ApiResponse(AppStatus.REJECT.label, HttpStatus.EXPECTATION_FAILED.value(),
//                "You are not Authorized");
//    }

    @Override
    /**
     * @Validating existingUser by Email
     * @Validate user password and change password
     * @Save the new password and return a Success Message
     */
    public ApiResponse<String> resetPassword(String email, changeUserPasswordRequest request) {
//        if (jwtFilter.isAdmin()) {
            AppUser users = userRepository.findByEmail(email);

            if (users.getPassword().equals(request.getOldPassword())) {
                users.setPassword(request.getNewPassword());

                userRepository.save(users);

                return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                        "Password Changed successfully");
            }
            return new ApiResponse(AppStatus.FAILED.label, HttpStatus.BAD_REQUEST.value(),
                    "Incorrect Old Password");
        }
//        return new ApiResponse(AppStatus.REJECT.label, HttpStatus.EXPECTATION_FAILED.value(),
//                "You are not Authorized");
//    }

    @Override
    /**
     * @Validating existingUser by Email
     * @Getiing email and password of user and sending it to User`s Email
     * @Return a Success Message
     */
    public ApiResponse<String> forgotUserPassword(String email) throws MessagingException {

        AppUser users = userRepository.findByEmail(email);

        emailUtils.forgotMail(users.getEmail(), "Your Booking Credentials by Hotel Management System", users.getPassword());

        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Check your email for credentials");

    }


    @Override
    /**
     * @Validating and finding existingUser by Email
     * @Validate user email and password
     * @Return a Success Message if email and password is correct
     * @Return a Failed Message if email and password is Incorrect
     */
    public ApiResponse<String> loginUser(String email, loginUserRequest request) {
        AppUser users = userRepository.findByEmail(email);

        if (users.getEmail().equals(request.getEmail())
                && users.getPassword().equals(request.getPassword())) {

            return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    "User login successfully");
        }

        return new ApiResponse(AppStatus.FAILED.label, HttpStatus.BAD_REQUEST.value(),
                "Incorrect Email or Password");
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

    private AppUser getLoggedInUser(Principal principal) {
        return userRepository.findByUsername(principal.getName()).get();
    }



}
