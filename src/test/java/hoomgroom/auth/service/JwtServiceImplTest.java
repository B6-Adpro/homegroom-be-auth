package hoomgroom.auth.service;

import hoomgroom.auth.model.Role;
import hoomgroom.auth.model.User;
import hoomgroom.auth.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceImplTest {

    private static final String SECRET_KEY = System.getenv("JWT_SECRET_KEY");
    @InjectMocks
    JwtServiceImpl service;

    @Mock
    UserRepository repository;

    @Mock
    private UserDetails userDetails;

    private User user;
    private String token;

    @BeforeEach
    void setUp() {

        user = User.builder()
                .id(UUID.fromString("e5e4d15a-f6db-4b33-a9f8-f8257e7451f6"))
                .username("testuser")
                .firstname("Test")
                .lastname("User")
                .role(Role.USER)
                .build();

        lenient().when(repository.findByUsername("testuser")).thenReturn(Optional.of(user));
        lenient().when(userDetails.getUsername()).thenReturn("testuser");

        service = new JwtServiceImpl(repository);
    }

    @Test
    void generateTokenTest() {
        token = service.generateToken(userDetails);
        assertNotNull(token);
    }

    @Test
    void extractUsernameTest() {
        token = service.generateToken(userDetails);
        String username = service.extractUsername(token);
        assertEquals("testuser", username);
    }

    @Test
    void isTokenValidTest() {
        token = service.generateToken(userDetails);
        boolean isValid = service.isTokenValid(token, userDetails);
        assertTrue(isValid);
    }

    @Test
    void isTokenInvalidDWrongUsernameTest() {
        token = service.generateToken(userDetails);
        when(userDetails.getUsername()).thenReturn("wronguser");
        boolean isValid = service.isTokenValid(token, userDetails);
        assertFalse(isValid);
    }

    @Test
    void isTokenInvalidExpiredTest() {
        token = Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() - 5000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

        assertThrows(io.jsonwebtoken.ExpiredJwtException.class, () -> service.isTokenValid(token, userDetails));
    }

    private Key getSignInKey() {
        byte[] keyBytes = java.util.Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Test
    void isTokenExpiredTest() {
        token = service.generateToken(userDetails);
        boolean isExpired = service.isTokenExpired(token);
        assertFalse(isExpired);
    }

    @Test
    void extractExpirationTest() {
        token = service.generateToken(userDetails);
        Date expiration = service.extractExpiration(token);
        assertNotNull(expiration);
    }

    @Test
    void extractClaimTest() {
        token = service.generateToken(userDetails);
        Claims claims = service.extractAllClaims(token);
        assertNotNull(claims);
    }
}
