package fcmb.com.good.services.others;

import fcmb.com.good.common.UserConstant;
import fcmb.com.good.config.GroupUserDetails;
import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.model.dto.request.othersRequest.AuthRequest;
import fcmb.com.good.model.entity.activityLog.ActivityLog;
import fcmb.com.good.model.entity.user.Users;
import fcmb.com.good.repo.activityLog.ActivityLogRepository;
import fcmb.com.good.repo.user.UsersRepository;
import fcmb.com.good.utills.JwtUtil;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class JwtAuthenticationServiceImpl implements JwtAuthenticationService{

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    private final UsersRepository usersRepository;

    private final ActivityLogRepository activityLogRepository;

    String username;

    String token;


    @Override
    public ResponseEntity<?> authenticateUsernameAndPassword(AuthRequest authRequest) {

        Optional<Users> existingUsersOptional = usersRepository.findUsersByUsername(authRequest.getUsername());
        if (existingUsersOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        Users appUser = existingUsersOptional.get();

        if (!appUser.getUsername().equals(authRequest.getUsername()) ||
                !appUser.getPassword().equals(authRequest.getPassword())) {
            throw new RecordNotFoundException("Invalid username or password");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if (authentication.isAuthenticated() )
            token = jwtUtil.generateToken(authRequest.getUsername());


        ActivityLog activityLog = new ActivityLog();
        activityLog.setName("user login");
        activityLog.setCategory("login");
        activityLog.setDescription("this is a login log");
        activityLog.setPerformedBy(appUser.getName());
        activityLog.setPerformedDate(LocalDateTime.now());

        activityLogRepository.save(activityLog);


        GroupUserDetails userDetails = (GroupUserDetails) authentication.getPrincipal();

        Map<String, Object> response = new HashMap<>();

        response.put("token", token);
        response.put("username", userDetails.getUsername());
        response.put("name", userDetails.getName());
        response.put("email", userDetails.getEmail());
        response.put("usersCategory", userDetails.getUsersCategory());
        response.put("phone", userDetails.getPhone());
        response.put("address", userDetails.getAddress());
        response.put("city", userDetails.getCity());
        response.put("gender", userDetails.getGender());
        response.put("roles", userDetails.getRoles());
        response.put("uuid", userDetails.getUuid());
        response.put("photo", userDetails.getPhoto());


        return ResponseEntity.ok(response);


    }


    /**
     * @validating userOptional by uuid*
     * @Validate if the List of user is empty otherwise return record not found
     * @return userOptional* *
     * * */
    private Users validateUser(UUID uuid) {
        Optional<Users> userOptional = usersRepository.findByUuid(uuid);
        if (userOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return userOptional.get();
    }


    @Override
    public String giveAccessToUser(UUID uuid, String userRole, Principal principal) {

        Users user = validateUser(uuid);

        List<String> activeRoles = getRolesByLoggedInUser(principal);

        String newRole = "";
        if (activeRoles.contains(userRole)) {
            newRole = user.getRoles() + "," + userRole;
            user.setRoles(newRole);
        }
        usersRepository.save(user);

        return "Hi " + user.getUsername() + " New Role assign to you by " + principal.getName();


    }



    private List<String> getRolesByLoggedInUser(Principal principal) {

        String roles = getNameOfLoggedInUser(principal).getRoles();

        List<String> assignRoles = Arrays.stream(roles.split(",")).collect(Collectors.toList());

        if (assignRoles.contains("ROLE_ADMIN")) {
            return Arrays.stream(UserConstant.ADMIN_ACCESS).collect(Collectors.toList());
        }
        if (assignRoles.contains("ROLE_MODERATOR")) {
            return Arrays.stream(UserConstant.MODERATOR_ACCESS).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }


    private Users getNameOfLoggedInUser(Principal principal) {
        return usersRepository.findUsersByUsername(principal.getName()).get();
    }






}
