package hoomgroom.auth.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {
    User user1;
    User user2;
    Collection<? extends GrantedAuthority> authorities1;
    Collection<? extends GrantedAuthority> authorities2;

    @BeforeEach
    void setUp() {
        user1 = User.builder().build();
        user1.setId(UUID.fromString("e5e4d15a-f6db-4b33-a9f8-f8257e7451f6"));
        user1.setUsername("user test");
        user1.setFirstname("first name test");
        user1.setLastname("last name test");
        user1.setPassword("password test");
        user1.setRole(Role.USER);

        user2 = User.builder()
                .id(UUID.fromString("76802533-72cc-4e5f-89cc-96610fa8eae1"))
                .username("user test 2")
                .firstname("first name test 2")
                .lastname("last name test 2")
                .password("password test 2")
                .role(Role.ADMIN)
                .build();

        authorities1 = user1.getAuthorities();
        authorities2 = user2.getAuthorities();
    }

    @Test
    void testGetUserID() {
        assertEquals(UUID.fromString("e5e4d15a-f6db-4b33-a9f8-f8257e7451f6"), user1.getId());
    }

    @Test
    void testGetUserIDWithBuilder() {
        assertEquals(UUID.fromString("76802533-72cc-4e5f-89cc-96610fa8eae1"), user2.getId());
    }

    @Test
    void testGetUserUsername() {
        assertEquals("user test", user1.getUsername());
    }

    @Test
    void testGetUserUsernameWithBuilder() {
        assertEquals("user test 2", user2.getUsername());
    }

    @Test
    void testGetUserFirstName() {
        assertEquals("first name test", user1.getFirstname());
    }

    @Test
    void testGetUserFirstNameWithBuilder() {
        assertEquals("first name test 2", user2.getFirstname());
    }

    @Test
    void testGetUserLastName() {
        assertEquals("last name test", user1.getLastname());
    }

    @Test
    void testGetUserLastNameWithBuilder() {
        assertEquals("last name test 2", user2.getLastname());
    }

    @Test
    void testGetUserPassword() {
        assertEquals("password test", user1.getPassword());
    }

    @Test
    void testGetUserPasswordWithBuilder() {
        assertEquals("password test 2", user2.getPassword());
    }

    @Test
    void testGetUserRole() {
        assertEquals(Role.USER, user1.getRole());
    }

    @Test
    void testGetUserRoleWithBuilder() {
        assertEquals(Role.ADMIN, user2.getRole());
    }

    @Test
    void testGetUserAccountIsNonExpired() {
        assertTrue(user1.isAccountNonExpired());
    }

    @Test
    void testGetUserAccountIsNonExpiredWithBuilder() {
        assertTrue(user2.isAccountNonExpired());
    }

    @Test
    void testGetUserAccountIsNonLocked() {
        assertTrue(user1.isAccountNonLocked());
    }

    @Test
    void testGetUserAccountIsNonLockedWithBuilder() {
        assertTrue(user2.isAccountNonLocked());
    }

    @Test
    void testGetUserCredentialIsNonLocked() {
        assertTrue(user1.isCredentialsNonExpired());
    }

    @Test
    void testGetUserCredentialIsNonLockedWithBuilder() {
        assertTrue(user2.isCredentialsNonExpired());
    }

    @Test
    void testGetUserIsEnabled() {
        assertTrue(user1.isEnabled());
    }

    @Test
    void testGetUserIsEnabledWithBuilder() {
        assertTrue(user2.isEnabled());
    }

    @Test
    void testGetUserAuthorities() {
        assertEquals(1, authorities1.size());
        assertTrue(authorities1.contains(new SimpleGrantedAuthority(user1.getRole().name())));
    }

    @Test
    void testGetUserAuthoritiesWithBuilder() {
        assertEquals(1, authorities2.size());
        assertTrue(authorities2.contains(new SimpleGrantedAuthority(user2.getRole().name())));
    }
}
