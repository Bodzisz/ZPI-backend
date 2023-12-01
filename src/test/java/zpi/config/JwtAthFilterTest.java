package zpi.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

class JwtAthFilterTest {

    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;
    @Mock
    private UserDetails userDetails;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;

    @InjectMocks
    private JwtAthFilter jwtAuthFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void shouldFilterChainProceedWhenAuthHeaderIsMissing() throws IOException, ServletException {
        when(request.getHeader(AUTHORIZATION)).thenReturn(null);

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(securityContext, never()).setAuthentication(any());
    }

    @Test
    void shouldAuthenticateWhenValidJwtTokenIsProvided() throws IOException, ServletException {
        String jwtToken = "Bearer validToken";
        String username = "user";

        when(request.getHeader(AUTHORIZATION)).thenReturn(jwtToken);
        when(jwtUtils.extractUsername("validToken")).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtUtils.validateToken("validToken", userDetails)).thenReturn(true);

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(securityContext, times(1)).setAuthentication(any());
    }

    @Test
    void shouldNotAuthenticateWhenInvalidJwtTokenIsProvided() throws IOException, ServletException {
        String jwtToken = "Bearer invalidToken";
        String username = "user";

        when(request.getHeader(AUTHORIZATION)).thenReturn(jwtToken);
        when(jwtUtils.extractUsername("invalidToken")).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtUtils.validateToken("invalidToken", userDetails)).thenReturn(false);

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(securityContext, never()).setAuthentication(any());
    }

}
