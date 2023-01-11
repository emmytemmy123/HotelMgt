package fcmb.com.good.services.user;


import fcmb.com.good.model.dto.request.othersRequest.AuthRequest;
import fcmb.com.good.model.dto.request.userRequest.UserRequest;
import fcmb.com.good.model.dto.request.userRequest.changeUserPasswordRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.userResponse.UserResponse;
import fcmb.com.good.model.dto.response.userResponse.changeUserPasswordResponse;
import fcmb.com.good.model.dto.response.userResponse.forgotUserPasswordResponse;

import javax.mail.MessagingException;
import java.util.UUID;


public interface UserService {

    ApiResponse<UserResponse> getListOfUsers(int page, int size);

    ApiResponse<UserResponse> addUsers(UserRequest request);

    ApiResponse<UserResponse> getUsersById(UUID userId);

    ApiResponse<UserResponse> updateUser( UUID userId, UserRequest request);

    ApiResponse<String> deleteUser(UUID userId);

    ApiResponse<changeUserPasswordResponse> changeUserPassword(String email, changeUserPasswordRequest request);

    ApiResponse<forgotUserPasswordResponse> forgotUserPassword(String email) throws MessagingException;

    ApiResponse<AuthRequest> authenticate(AuthRequest authRequest);






}
