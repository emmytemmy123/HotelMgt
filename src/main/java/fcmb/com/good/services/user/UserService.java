package fcmb.com.good.services.user;


import fcmb.com.good.model.dto.request.othersRequest.AuthRequest;
import fcmb.com.good.model.dto.request.userRequest.UserRequest;
import fcmb.com.good.model.dto.request.userRequest.changeUserPasswordRequest;
import fcmb.com.good.model.dto.request.userRequest.loginUserRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.userResponse.UserResponse;
import fcmb.com.good.model.dto.response.userResponse.changeUserPasswordResponse;
import fcmb.com.good.model.dto.response.userResponse.forgotUserPasswordResponse;

import javax.mail.MessagingException;
import java.util.List;
import java.util.UUID;


public interface UserService {

    ApiResponse<List<UserResponse>> getListOfUsers(int page, int size);

    ApiResponse<String> addUsers(UserRequest request);

    ApiResponse<UserResponse> getUsersById(UUID userId);

    ApiResponse<String> updateUser( UUID userId, UserRequest request);

    ApiResponse<String> deleteUser(UUID userId);

    ApiResponse<String> changeUserPassword(String email, changeUserPasswordRequest request);

    ApiResponse<String> forgotUserPassword(String email) throws MessagingException;

    ApiResponse<AuthRequest> authenticate(AuthRequest authRequest);

    ApiResponse<String> loginUser(String email, loginUserRequest request);





}
