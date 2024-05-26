package hoomgroom.auth.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthenticationResponseTest {

    @Test
    void testBuilder() {
        String token = "token test";

        AuthenticationResponse authResponse = AuthenticationResponse.builder()
                .token(token)
                .build();

        assertEquals(token, authResponse.getToken());
    }

    @Test
    void testToString() {
        assertEquals("AuthenticationResponse.AuthenticationResponseBuilder(message=null, token=null)",
                AuthenticationResponse.builder().toString());
    }
}
