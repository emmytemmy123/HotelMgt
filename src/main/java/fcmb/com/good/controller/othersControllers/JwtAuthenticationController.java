package fcmb.com.good.controller.othersControllers;


import fcmb.com.good.common.UserConstant;
import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.model.dto.request.othersRequest.AuthRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.userResponse.CustomerResponse;
import fcmb.com.good.model.entity.user.AppUser;
import fcmb.com.good.repo.user.UserRepository;
import fcmb.com.good.services.others.JwtAuthenticationService;
import fcmb.com.good.utills.MessageUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import static fcmb.com.good.utills.EndPoints.UsersEndPoints.*;
import static fcmb.com.good.utills.EndpointParam.*;
import static fcmb.com.good.utills.EndpointParam.SIZE_DEFAULT;

@RestController
@RequiredArgsConstructor
public class JwtAuthenticationController {

    private final UserRepository userRepository;

    private final JwtAuthenticationService jwtAuthenticationService;




    @PostMapping(AUTHENTICATE_USER)
    @ApiOperation(value = "Endpoint for authenticate Username And Password ", response = String.class)
    public String authenticateUsernameAndPassword(@Valid @RequestBody AuthRequest request) throws IOException {
        return jwtAuthenticationService.authenticateUsernameAndPassword(request);
    }


    @GetMapping(GIVE_ACCESS_TO_USER)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    @ApiOperation(value = "Endpoint for giving access to users")
    public String giveAccessToUser(@PathVariable UUID uuid, @PathVariable String userRole, Principal principal) {
        return jwtAuthenticationService.giveAccessToUser(uuid, userRole,principal);
    }






}
