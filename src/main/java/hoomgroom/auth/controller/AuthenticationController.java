package hoomgroom.auth.controller;

import hoomgroom.auth.dto.AuthenticationRequest;
import hoomgroom.auth.dto.AuthenticationResponse;
import hoomgroom.auth.dto.RegisterRequest;
import hoomgroom.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public CompletableFuture<ResponseEntity<AuthenticationResponse>> register (
            @RequestBody RegisterRequest request
    ) {
        return authenticationService.register(request)
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping("/login")
    public CompletableFuture<ResponseEntity<AuthenticationResponse>> login (
            @RequestBody AuthenticationRequest request
    ) {
        return authenticationService.authenticate(request)
                .thenApply(ResponseEntity::ok);
    }
}
