package hoomgroom.auth.service;

import hoomgroom.auth.dto.AuthenticationRequest;
import hoomgroom.auth.dto.AuthenticationResponse;
import hoomgroom.auth.dto.RegisterRequest;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public interface AuthenticationService {
    CompletableFuture<AuthenticationResponse> register(RegisterRequest request);
    CompletableFuture<AuthenticationResponse> authenticate(AuthenticationRequest request);

}
