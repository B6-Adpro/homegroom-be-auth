package hoomgroom.auth.controller;

import hoomgroom.auth.dto.AuthenticationRequest;
import hoomgroom.auth.dto.AuthenticationResponse;
import hoomgroom.auth.dto.RegisterRequest;
import hoomgroom.auth.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    public AuthenticationControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() throws ExecutionException, InterruptedException {
        RegisterRequest registerRequest =
                RegisterRequest
                    .builder()
                    .username("username test")
                    .firstname("firstname test")
                    .lastname("lastname test")
                    .password("password test")
                    .build();

        AuthenticationResponse expectedResponse = AuthenticationResponse.builder().token("token").build();
        when(authenticationService.register(registerRequest)).thenReturn(CompletableFuture.completedFuture(expectedResponse));

        CompletableFuture<ResponseEntity<AuthenticationResponse>> responseEntity = authenticationController.register(registerRequest);

        assertEquals(HttpStatus.OK, responseEntity.get().getStatusCode());
        assertEquals(expectedResponse, responseEntity.get().getBody());
        verify(authenticationService, times(1)).register(registerRequest);
    }

    @Test
    void testLogin() throws ExecutionException, InterruptedException {
        AuthenticationRequest authenticationRequest =
                AuthenticationRequest
                    .builder()
                    .username("username test")
                    .password("password test")
                    .build();

        AuthenticationResponse expectedResponse = AuthenticationResponse.builder().token("token").build();
        when(authenticationService.authenticate(authenticationRequest)).thenReturn(CompletableFuture.completedFuture(expectedResponse));

        CompletableFuture<ResponseEntity<AuthenticationResponse>> responseEntity = authenticationController.login(authenticationRequest);

        assertEquals(HttpStatus.OK, responseEntity.get().getStatusCode());
        assertEquals(expectedResponse, responseEntity.get().getBody());
        verify(authenticationService, times(1)).authenticate(authenticationRequest);
    }
}
