package hoomgroom.auth.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthenticationRequestTest {

    @Test
    public void testBuilder() {
        String username = "testUser";
        String password = "testPassword";

        AuthenticationRequest authRequest = AuthenticationRequest.builder()
                .username(username)
                .password(password)
                .build();

        assertEquals(username, authRequest.getUsername());
        assertEquals(password, authRequest.getPassword());
    }

    @Test
    public void testToString() {
        assertEquals("AuthenticationRequest.AuthenticationRequestBuilder(username=null, password=null)",
                AuthenticationRequest.builder().toString());
    }
}
