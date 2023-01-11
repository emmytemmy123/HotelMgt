package fcmb.com.good.services.user;


import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.filter.JwtFilter;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.othersRequest.AuthRequest;
import fcmb.com.good.model.dto.request.userRequest.UserRequest;
import fcmb.com.good.model.dto.request.userRequest.changeUserPasswordRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.userResponse.CustomerResponse;
import fcmb.com.good.model.dto.response.userResponse.UserResponse;
import fcmb.com.good.model.dto.response.userResponse.changeUserPasswordResponse;
import fcmb.com.good.model.dto.response.userResponse.forgotUserPasswordResponse;
import fcmb.com.good.model.entity.user.User;
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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl  implements UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final JwtFilter jwtFilter;
    private final EmailUtils emailUtils;


    @Override
    public ApiResponse<AuthRequest> authenticate(AuthRequest authRequest)  {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if(!authentication.isAuthenticated()){
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        }
            jwtUtil.generateToken(authRequest.getUsername(), authRequest.getRole());

        return new ApiResponse<AuthRequest>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(authentication,AuthRequest.class));
    }

    @Override
    public ApiResponse<UserResponse> getListOfUsers(int page, int size) {
        if(jwtFilter.isAdmin() || jwtFilter.isEmployee()) {
            List<User> userList = userRepository.findAll(PageRequest.of(page, size)).toList();
            if (userList.isEmpty())
                throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

              return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(userList, UserResponse.class));
        }
            return new ApiResponse(AppStatus.REJECT.label, HttpStatus.EXPECTATION_FAILED.value(),
                    "You are not Authorized");

    }

    @Override
    public ApiResponse<UserResponse> addUsers(@RequestBody UserRequest request) {
        if(jwtFilter.isAdmin()){
            Optional<User> user  = validateUserByEmailId(request.getEmail());
            if(!user.isEmpty()){
                return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
                        "Email Already Exist");
            }
            userRepository.save(getUserFromRequest(request));
            return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    "Record Added successfully");
        }
        return new ApiResponse(AppStatus.REJECT.label, HttpStatus.EXPECTATION_FAILED.value(),
                "You are not Authorized");

    }


    @Override
    public ApiResponse<UserResponse> getUsersById(@RequestParam("id") UUID userId) {
        if(jwtFilter.isAdmin()){
            Optional<User> user = userRepository.findByUuid(userId);
            if(user.isEmpty())
                throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

            User us = user.get();
            return new ApiResponse<UserResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    Mapper.convertObject(us,UserResponse.class));
        }
        return new ApiResponse(AppStatus.REJECT.label, HttpStatus.EXPECTATION_FAILED.value(),
                "You are not Authorized");
    }

    private User validateUser(UUID uuid){
        Optional<User> user = userRepository.findByUuid(uuid);
        if(user.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return user.get();
    }

    private Optional<User> validateUserByEmailId(String email){
        Optional<User> user = userRepository.findByEmailId(email);
        System.out.println(user);
        return user;
    }

    private User getUserFromRequest(UserRequest request){
        User us = new User();
        us.setPostedBy(request.getPostedBy());
        us.setName(request.getName());
        us.setEmail(request.getEmail());
        us.setAddress(request.getAddress());
        us.setCountry(request.getCountry());
        us.setCity(request.getCity());
        us.setGender(request.getGender());
        us.setPassword(request.getPassword());
        us.setPhone(request.getPhone());
        us.setUsername(request.getUsername());
        us.setPhoto(request.getPhoto());
        us.setRole(request.getRole());
        //userRepository.save(us);
        return us;
    }

    private User postedByUuid(String postedBy){
        User user = validateUser(UUID.fromString(postedBy));
        User users = new User();
        users.setPostedBy(String.valueOf(users));
        return user;
    }


   @Override
    public ApiResponse<UserResponse> updateUser(UUID userId, @RequestBody UserRequest request) {
        if(jwtFilter.isAdmin()){
            User user = validateUser(userId);
            user.setPostedBy(request.getPostedBy());
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
            user.setRole(request.getRole());

            userRepository.save(user);
            return new ApiResponse<UserResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    Mapper.convertObject(user,UserResponse.class));
        }
            return new ApiResponse(AppStatus.REJECT.label, HttpStatus.EXPECTATION_FAILED.value(),
                    "You are not Authorized");
    }

    @Override
    public ApiResponse<String> deleteUser(UUID userId) {
        if(jwtFilter.isAdmin()){
            User user = validateUser(userId);
            userRepository.delete(user);
            return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    "Record Deleted successfully");
        }
        return new ApiResponse(AppStatus.REJECT.label, HttpStatus.EXPECTATION_FAILED.value(),
                "You are not Authorized");
    }

    @Override
    public ApiResponse<changeUserPasswordResponse> changeUserPassword(String email,  changeUserPasswordRequest request) {
       if(jwtFilter.isAdmin()){
           User users = userRepository.findByEmail(email);
           if(users.getPassword().equals(request.getOldPassword())){
               users.setPassword(request.getNewPassword());
               userRepository.save(users);

               return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                       "Password Changed successfully");
           }
           return new ApiResponse(AppStatus.FAILED.label, HttpStatus.BAD_REQUEST.value(),
                   "Incorrect Old Password");
       }
        return new ApiResponse(AppStatus.REJECT.label, HttpStatus.EXPECTATION_FAILED.value(),
                "You are not Authorized");
    }

    @Override
    public ApiResponse<forgotUserPasswordResponse> forgotUserPassword(String email) throws MessagingException {
        User users = userRepository.findByEmail(email);
            emailUtils.forgotMail(users.getEmail(), "Credentials by Hotel Management System", users.getPassword() );

        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Check your email for credentials");
    }


}
