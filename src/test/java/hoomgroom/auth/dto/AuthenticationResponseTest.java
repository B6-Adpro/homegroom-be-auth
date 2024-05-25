package hoomgroom.auth.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthenticationResponseTest {

    @Test
    public void testBuilder() {
        String token = "token test";

        AuthenticationResponse authResponse = AuthenticationResponse.builder()
                .token(token)
                .build();

        assertEquals(token, authResponse.getToken());
    }

    @Test
    public void testToString() {
        assertEquals("AuthenticationResponse.AuthenticationResponseBuilder(token=null)",
                AuthenticationResponse.builder().toString());
    }
}
