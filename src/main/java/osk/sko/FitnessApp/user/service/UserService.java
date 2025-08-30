package osk.sko.FitnessApp.user.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import osk.sko.FitnessApp.config.JwtService;
import osk.sko.FitnessApp.exception.UnauthorizedAccessException;
import osk.sko.FitnessApp.user.advice.UserAlreadyExistsException;
import osk.sko.FitnessApp.user.dto.AuthRequest;
import osk.sko.FitnessApp.user.dto.AuthResponse;
import osk.sko.FitnessApp.user.dto.RegisterRequest;
import osk.sko.FitnessApp.user.dto.UserDTO;
import osk.sko.FitnessApp.user.mapper.UserMapper;
import osk.sko.FitnessApp.user.model.User;
import osk.sko.FitnessApp.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    @Value("${server.servlet.session.cookie.secure}")
    private boolean secureCookiesEnabled;
    @Value("${server.servlet.session.cookie.same-site}")
    private String sameSite;

    public UserDTO register(RegisterRequest registerRequest) {
        if(userRepository.findUserByEmail(registerRequest.getEmail()).isEmpty()) {
            User user = new User(
                    registerRequest.getEmail(),
                    registerRequest.getName(),
                    registerRequest.getLastName(),
                    registerRequest.getPhoneNumber(),
                    passwordEncoder.encode(registerRequest.getPassword()),
                    "ROLE_USER"
            );
            userRepository.save(user);
            return userMapper.toDTO(user);
        } else {
            throw new UserAlreadyExistsException("User with that email already exists");
        }
    }

    public AuthResponse authenticate(AuthRequest authRequestDTO, HttpServletResponse response) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                authRequestDTO.getEmail(),
                                authRequestDTO.getPassword())
                );

        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(authRequestDTO.getEmail());

            ResponseCookie cookie = ResponseCookie.from("token", token)
                    .httpOnly(true)
                    .secure(secureCookiesEnabled)
                    .path("/")
                    .sameSite(sameSite)
                    .maxAge(24 * 60 * 60)
                    .build();
            response.addHeader("Set-Cookie", cookie.toString());

            User user = userRepository.findUserByEmail(authRequestDTO.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));


            UserDTO userDTO = userMapper.toDTO(user);

            return new AuthResponse(token, userDTO);

        } else {
            throw new UnauthorizedAccessException("Invalid email or password");
        }
    }

    public void logout(HttpServletResponse response) {
        ResponseCookie deleteCookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                .secure(secureCookiesEnabled)
                .path("/")
                .sameSite(sameSite)
                .maxAge(0)
                .build();
        response.addHeader("Set-Cookie", deleteCookie.toString());
    }



}
