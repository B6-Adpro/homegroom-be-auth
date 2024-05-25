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
    void testRegister() {
        RegisterRequest registerRequest =
                RegisterRequest
                    .builder()
                    .username("username test")
                    .firstname("firstname test")
                    .lastname("lastname test")
                    .password("password test")
                    .build();

        AuthenticationResponse expectedResponse = AuthenticationResponse.builder().token("token").build();
        when(authenticationService.register(registerRequest)).thenReturn(expectedResponse);

        ResponseEntity<AuthenticationResponse> responseEntity = authenticationController.register(registerRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
        verify(authenticationService, times(1)).register(registerRequest);
    }

    @Test
    void testLogin() {
        AuthenticationRequest authenticationRequest =
                AuthenticationRequest
                    .builder()
                    .username("username test")
                    .password("password test")
                    .build();

        AuthenticationResponse expectedResponse = AuthenticationResponse.builder().token("token").build();
        when(authenticationService.authenticate(authenticationRequest)).thenReturn(expectedResponse);

        ResponseEntity<AuthenticationResponse> responseEntity = authenticationController.login(authenticationRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
        verify(authenticationService, times(1)).authenticate(authenticationRequest);
    }
}
