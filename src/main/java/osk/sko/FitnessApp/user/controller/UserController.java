package osk.sko.FitnessApp.user.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import osk.sko.FitnessApp.user.dto.AuthRequest;
import osk.sko.FitnessApp.user.dto.AuthResponse;
import osk.sko.FitnessApp.user.dto.RegisterRequest;
import osk.sko.FitnessApp.user.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest registerRequest) {
        userService.register(registerRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest, HttpServletResponse response) {
        return ResponseEntity.ok(userService.authenticate(authRequest, response));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        userService.logout(response);
        return ResponseEntity.ok().build();
    }
}
