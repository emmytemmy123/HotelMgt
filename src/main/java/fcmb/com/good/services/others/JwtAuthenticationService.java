package fcmb.com.good.services.others;

import fcmb.com.good.model.dto.request.othersRequest.AuthRequest;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.UUID;

public interface JwtAuthenticationService {

    ResponseEntity<?> authenticateUsernameAndPassword(AuthRequest authRequest);

    String giveAccessToUser(UUID uuid, String userRole, Principal principal);



}
