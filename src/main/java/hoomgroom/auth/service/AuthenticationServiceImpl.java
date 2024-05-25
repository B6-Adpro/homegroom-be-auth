package hoomgroom.auth.service;

import hoomgroom.auth.dto.AuthenticationRequest;
import hoomgroom.auth.dto.AuthenticationResponse;
import hoomgroom.auth.dto.RegisterRequest;
import hoomgroom.auth.model.Role;
import hoomgroom.auth.model.User;
import hoomgroom.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .role(Role.USER)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            return AuthenticationResponse.builder().message("Username must be unique!").build();
        }

        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().message("Register Success!").token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            return AuthenticationResponse.builder().message("Login Gagal!").build();
        }


        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().message("Login Success!").token(jwtToken).build();
    }
}
