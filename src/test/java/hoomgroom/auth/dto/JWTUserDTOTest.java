package hoomgroom.auth.dto;

import hoomgroom.auth.model.Role;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JWTUserDTOTest {

    @Test
    void testBuilder() {
        UUID id = UUID.fromString("d822a6d1-3e86-4a31-b6c2-204ae9944507");
        String username = "username";
        String firstname = "firstname";
        String lastname = "lastname";
        Role role = Role.USER;

        JWTUserDTO userDTO = JWTUserDTO.builder()
                .id(id)
                .username(username)
                .firstname(firstname)
                .lastname(lastname)
                .role(role)
                .build();

        assertEquals(id, userDTO.getId());
        assertEquals(username, userDTO.getUsername());
        assertEquals(firstname, userDTO.getFirstname());
        assertEquals(lastname, userDTO.getLastname());
        assertEquals(role, userDTO.getRole());
    }

    @Test
    void testToString() {
        assertEquals("JWTUserDTO.JWTUserDTOBuilder(id=null, username=null, firstname=null, lastname=null, role=null)",
                JWTUserDTO.builder().toString());
    }
}
