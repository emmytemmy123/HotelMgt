package fcmb.com.good.controller.othersControllers;


import fcmb.com.good.model.dto.request.othersRequest.AuthRequest;
import fcmb.com.good.repo.user.UsersRepository;
import fcmb.com.good.services.others.JwtAuthenticationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

import static fcmb.com.good.utills.EndPoints.UsersEndPoints.*;

@RestController
@RequiredArgsConstructor
public class JwtAuthenticationController {

    private final UsersRepository usersRepository;

    private final JwtAuthenticationService jwtAuthenticationService;




    @PostMapping(AUTHENTICATE_USER)
    @ApiOperation(value = "Endpoint for authenticate Username And Password ", response = String.class)
    public ResponseEntity<?> authenticateUsernameAndPassword(@Valid @RequestBody AuthRequest request) throws IOException {
        return jwtAuthenticationService.authenticateUsernameAndPassword(request);
    }


    @GetMapping(GIVE_ACCESS_TO_USER)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    @ApiOperation(value = "Endpoint for giving access to users")
    public String giveAccessToUser(@PathVariable UUID uuid, @PathVariable String userRole, Principal principal) {
        return jwtAuthenticationService.giveAccessToUser(uuid, userRole,principal);
    }






}
