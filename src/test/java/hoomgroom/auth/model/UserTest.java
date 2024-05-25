package hoomgroom.auth.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {
    User user;
    Collection<? extends GrantedAuthority> authorities1;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(UUID.fromString("e5e4d15a-f6db-4b33-a9f8-f8257e7451f6"))
                .username("user test")
                .firstname("first name test")
                .lastname("last name test")
                .password("password test")
                .role(Role.USER)
                .build();

        authorities1 = user.getAuthorities();

    }

    @Test
    void testGetUserID() {
        assertEquals(UUID.fromString("e5e4d15a-f6db-4b33-a9f8-f8257e7451f6"), user.getId());
    }

    @Test
    void testGetUserUsername() {
        assertEquals("user test", user.getUsername());
    }

    @Test
    void testGetUserFirstName() {
        assertEquals("first name test", user.getFirstname());
    }

    @Test
    void testGetUserLastName() {
        assertEquals("last name test", user.getLastname());
    }

    @Test
    void testGetUserPassword() {
        assertEquals("password test", user.getPassword());
    }

    @Test
    void testGetUserRole() {
        assertEquals(Role.USER, user.getRole());
    }

    @Test
    void testGetUserAccountIsNonExpired() {
        assertTrue(user.isAccountNonExpired());
    }

    @Test
    void testGetUserAccountIsNonLocked() {
        assertTrue(user.isAccountNonLocked());
    }


    @Test
    void testGetUserCredentialIsNonLocked() {
        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    void testGetUserIsEnabled() {
        assertTrue(user.isEnabled());
    }


    @Test
    void testGetUserAuthorities() {
        assertEquals(1, authorities1.size());
        assertTrue(authorities1.contains(new SimpleGrantedAuthority(user.getRole().name())));
    }
}
