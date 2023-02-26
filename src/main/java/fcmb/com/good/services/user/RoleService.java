package fcmb.com.good.services.user;


import fcmb.com.good.model.dto.request.userRequest.RoleRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.userResponse.RoleResponse;
import fcmb.com.good.model.entity.user.Role;

import java.util.List;
import java.util.UUID;

public interface RoleService {

    ApiResponse<List<RoleResponse>> getListOfRoles(int page, int size);

    ApiResponse<String> addRole(RoleRequest request);

    ApiResponse<RoleResponse> getRolesById(UUID roleId);

    ApiResponse<String> updateRole(UUID roleId, RoleRequest request);

    ApiResponse<String> deleteRole(UUID roleId);

}
