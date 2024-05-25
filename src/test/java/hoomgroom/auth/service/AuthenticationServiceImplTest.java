package hoomgroom.auth.service;

import hoomgroom.auth.dto.AuthenticationRequest;
import hoomgroom.auth.dto.AuthenticationResponse;
import hoomgroom.auth.dto.RegisterRequest;
import hoomgroom.auth.model.Role;
import hoomgroom.auth.model.User;
import hoomgroom.auth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtServiceImpl jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private User user;
    private RegisterRequest registerRequest;
    private AuthenticationRequest authenticationRequest;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .username("testUser")
                .firstname("Test")
                .lastname("User")
                .password("testPassword")
                .role(Role.USER)
                .build();

        registerRequest = RegisterRequest.builder()
                .username("testUser")
                .firstname("Test")
                .lastname("User")
                .password("testPassword")
                .build();

        authenticationRequest = AuthenticationRequest.builder()
                .username("testUser")
                .password("testPassword")
                .build();
    }

    @Test
    void testRegister() {
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

        AuthenticationResponse authenticationResponse = authenticationService.register(registerRequest);

        assertNotNull(authenticationResponse);
        assertEquals("jwtToken", authenticationResponse.getToken());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testAuthenticate() {
        when(userRepository.findByUsername(authenticationRequest.getUsername())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("jwtToken");

        doAnswer(invocation -> null).when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        AuthenticationResponse response = authenticationService.authenticate(authenticationRequest);

        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByUsername(authenticationRequest.getUsername());
    }
}
