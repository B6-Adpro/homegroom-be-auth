package hoomgroom.auth.config;

import hoomgroom.auth.service.JwtServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtServiceImpl jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void testDoFilterInternalValidToken() throws ServletException, IOException {
        String token = "valid-jwt-token";
        String username = "user";
        String authHeader = "Bearer" + token;

        UserDetails userDetails = mock(UserDetails.class);
        when(request.getHeader("Authorization")).thenReturn(authHeader);
        when(jwtService.extractUsername(token)).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtService.isTokenValid(token, userDetails)).thenReturn(true);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(jwtService, times(1)).extractUsername(token);
        verify(userDetailsService, times(1)).loadUserByUsername(username);
        verify(jwtService, times(1)).isTokenValid(token, userDetails);
        verify(filterChain, times(1)).doFilter(request, response);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }


    @Test
    void testDoFilterInternalInvalidTokenNoUser() throws ServletException, IOException {
        String token = "invalid-jwt-token";
        String authHeader = "Bearer" + token;

        when(request.getHeader("Authorization")).thenReturn(authHeader);
        when(jwtService.extractUsername(token)).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(jwtService, times(1)).extractUsername(token);
        verify(userDetailsService, never()).loadUserByUsername(anyString());
        verify(jwtService, never()).isTokenValid(anyString(), any(UserDetails.class));
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternalNoAuthHeader() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(jwtService, never()).extractUsername(anyString());
        verify(userDetailsService, never()).loadUserByUsername(anyString());
        verify(jwtService, never()).isTokenValid(anyString(), any(UserDetails.class));
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternalAuthHeaderNotPrefixed() throws Exception {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(request.getHeader("Authorization")).thenReturn("invalid-token");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternalInvalidToken() throws Exception {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
        UserDetails userDetails = mock(UserDetails.class);

        when(request.getHeader("Authorization")).thenReturn("Bearerinvalid-token");

        when(jwtService.extractUsername("invalid-token")).thenReturn("user");
        when(userDetailsService.loadUserByUsername("user")).thenReturn(userDetails);
        when(jwtService.isTokenValid("invalid-token", userDetails)).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternalUsernameNotNullAndSecurityContextNotNull() throws ServletException, IOException {
        String token = "valid-jwt-token";
        String authHeader = "Bearer" + token;

        UserDetails userDetails = mock(UserDetails.class);
        when(request.getHeader("Authorization")).thenReturn(authHeader);
        when(jwtService.extractUsername(token)).thenReturn("user");
        lenient().when(userDetailsService.loadUserByUsername("user")).thenReturn(userDetails);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        lenient().when(securityContext.getAuthentication()).thenReturn(authentication);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(jwtService, times(1)).extractUsername(token);
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternalRequestMissing() {
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);

        assertThrows(NullPointerException.class, () ->
                jwtAuthenticationFilter.doFilterInternal(null, response, filterChain)
        );
    }

    @Test
    void testDoFilterInternalResponseMissing() {
        request = mock(HttpServletRequest.class);
        filterChain = mock(FilterChain.class);

        assertThrows(NullPointerException.class, () ->
                jwtAuthenticationFilter.doFilterInternal(request, null, filterChain)
        );
    }

    @Test
    void testDoFilterInternalFilterChainMissing() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        assertThrows(NullPointerException.class, () ->
                jwtAuthenticationFilter.doFilterInternal(request, response, null)
        );
    }
}
