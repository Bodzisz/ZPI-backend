package zpi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zpi.config.JwtUtils;
import zpi.dto.AuthenticationRequest;
import zpi.dto.AuthenticationResponse;
import zpi.entity.User;
import zpi.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final JwtUtils jwtUtils;


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.login(), request.password()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.login());
        Optional<User> user = userService.getByLogin(request.login());
        if (userDetails != null && user.isPresent()) {
            return ResponseEntity.ok(new AuthenticationResponse(jwtUtils.generateToken(userDetails), userDetails.getUsername(),
                    user.get().getFirstName(), user.get().getLastName(), user.get().getRole()));
        }
        return ResponseEntity.status(400).build();
    }
}
