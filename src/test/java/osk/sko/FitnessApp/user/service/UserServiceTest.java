package osk.sko.FitnessApp.user.service;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Unit Tests")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserMapper userMapper;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;

    private RegisterRequest registerRequest;
    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        this.registerRequest = new RegisterRequest(1,
                "test@email.com",
                "John",
                "Doe",
                "1234567890",
                "password");
        this.user = User.builder()
                .email(registerRequest.getEmail())
                .name(registerRequest.getName())
                .lastName(registerRequest.getLastName())
                .phoneNumber(registerRequest.getPhoneNumber())
                .password("encodedPassword")
                .authority("ROLE_USER")
                .workouts(null)
                .build();
        this.userDTO = UserDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .authority(user.getAuthority())
                .workouts(null)
                .build();
    }

    @DisplayName("Register Tests")
    @Nested
    class RegisterTests {

        @Test
        @DisplayName("Should register user when email is unique")
        void shouldRegisterUserWhenEmailIsUnique() {
            // Given
            when(userRepository.findUserByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
            when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
            when(userRepository.save(any(User.class))).thenReturn(user);
            when(userMapper.toDTO(user)).thenReturn(userDTO);

            // When
            final UserDTO result = userService.register(registerRequest);

            // Then
            verify(userRepository, times(1)).findUserByEmail(registerRequest.getEmail());
            verify(userRepository, times(1)).save(any(User.class));
            verify(passwordEncoder, times(1)).encode(registerRequest.getPassword());
            verify(userMapper, times(1)).toDTO(user);
            assertNotNull(result);
            assertEquals(userDTO, result);
            assertEquals(userDTO.getEmail(), result.getEmail());
            assertEquals(userDTO.getName(), result.getName());
            assertEquals(userDTO.getLastName(), result.getLastName());
            assertEquals(userDTO.getPhoneNumber(), result.getPhoneNumber());
            assertEquals(userDTO.getAuthority(), result.getAuthority());
        }

        @Test
        @DisplayName("Should throw exception when email already exists")
        void shouldThrowExceptionWhenEmailAlreadyExists() {
            // Given
            when(userRepository.findUserByEmail(registerRequest.getEmail())).thenReturn(Optional.of(user));

            // When & Then
            UserAlreadyExistsException result = assertThrows(
                    UserAlreadyExistsException.class,
                    () -> userService.register(registerRequest)
            );

            verify(userRepository, times(1)).findUserByEmail(registerRequest.getEmail());
            verify(userRepository, never()).save(any(User.class));
            verifyNoInteractions(userMapper);
            verifyNoInteractions(passwordEncoder);
            assertEquals(UserAlreadyExistsException.class, result.getClass());
            assertEquals("User with that email already exists", result.getMessage());
        }
    }

    @DisplayName("Authenticate Tests")
    @Nested
    class AuthenticateTests {

        @Test
        @DisplayName("Should authenticate user when credentials are valid")
        void shouldAuthenticateUserWhenCredentialsAreValid() {
            // Given
            final AuthRequest authRequest = new AuthRequest("test@email.com", "password");
            final String token = "jwtToken";
            final Authentication authentication = mock(Authentication.class);
            final HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
            final AuthResponse authResponse = new AuthResponse(token, userDTO);

            when(authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()))).thenReturn(authentication);
            when(authentication.isAuthenticated()).thenReturn(true);
            when(jwtService.generateToken(authRequest.getEmail())).thenReturn(token);
            when(userRepository.findUserByEmail(authRequest.getEmail())).thenReturn(Optional.of(user));
            when(userMapper.toDTO(user)).thenReturn(userDTO);

            // When
            userService.authenticate(authRequest, httpServletResponse);

            // Then
            verify(authenticationManager, times(1)).authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()));
            verify(authentication, times(1)).isAuthenticated();
            verify(jwtService, times(1)).generateToken(authRequest.getEmail());
            verify(userRepository, times(1)).findUserByEmail(authRequest.getEmail());
            verify(userMapper, times(1)).toDTO(user);
            verify(httpServletResponse, times(1)).addHeader(eq("Set-Cookie"), contains(token));
            assertEquals("jwtToken", authResponse.getToken());
            assertEquals(userDTO, authResponse.getUser());

        }

        @Test
        @DisplayName("Should throw exception when credentials are invalid")
        void shouldThrowExceptionWhenCredentialsAreInvalid() {
            // Given
            final AuthRequest authRequest = new AuthRequest("test@email.com", "password");
            final Authentication authentication = mock(Authentication.class);
            final HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

            when(authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()))).thenReturn(authentication);
            when(authentication.isAuthenticated()).thenReturn(false);

            // When & Then
            UnauthorizedAccessException exception = assertThrows(
                    UnauthorizedAccessException.class,
                    () -> userService.authenticate(authRequest, httpServletResponse));

            verify(authenticationManager, times(1)).authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()));
            verify(authentication, times(1)).isAuthenticated();
            verifyNoMoreInteractions(jwtService);
            verifyNoMoreInteractions(userRepository);
            verifyNoMoreInteractions(userMapper);
            verifyNoInteractions(httpServletResponse);
            assertEquals(UnauthorizedAccessException.class, exception.getClass());
            assertEquals("Invalid email or password", exception.getMessage());
        }
    }

    @DisplayName("Logout Tests")
    @Nested
    class LogoutTests {
        @Test
        @DisplayName("Should logout user by clearing the token cookie")
        void shouldLogoutUserByClearingTheTokenCookie() {
            // Given
            final HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

            // When
            userService.logout(httpServletResponse);

            // Then
            verify(httpServletResponse, times(1)).addHeader(eq("Set-Cookie"), contains("token=;"));
        }
    }
}