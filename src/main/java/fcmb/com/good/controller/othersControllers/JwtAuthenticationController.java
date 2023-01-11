package fcmb.com.good.controller.othersControllers;


import fcmb.com.good.model.dto.request.othersRequest.AuthRequest;
import fcmb.com.good.services.user.UserService;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.utills.JwtUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JwtAuthenticationController {

//    private final UserService userService;

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;



//    @PostMapping("/authentic")
//    @ApiOperation(value = "Endpoint for Authentication", response = String.class)
//    public ApiResponse<AuthRequest> authenticate(@RequestBody AuthRequest authRequest) {
//        return userService.authenticate(authRequest);
//    }



    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("Invalid username/password");
        }
        return jwtUtil.generateToken(authRequest.getUsername(), authRequest.getRole());
    }


}
