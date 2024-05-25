package hoomgroom.auth.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegisterRequestTest {

    @Test
    void testBuilder() {
        String username = "username";
        String firstname = "firstname";
        String lastname = "lastname";
        String password = "password";

        RegisterRequest request = RegisterRequest.builder()
                .username(username)
                .firstname(firstname)
                .lastname(lastname)
                .password(password)
                .build();

        assertEquals(username, request.getUsername());
        assertEquals(firstname, request.getFirstname());
        assertEquals(lastname, request.getLastname());
        assertEquals(password, request.getPassword());
    }

    @Test
    void testToString() {
        assertEquals("RegisterRequest.RegisterRequestBuilder(username=null, firstname=null, lastname=null, password=null)",
                RegisterRequest.builder().toString());
    }
}
