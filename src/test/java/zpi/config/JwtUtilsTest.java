package zpi.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilsTest {

    private JwtUtils jwtUtils;
    private final String SECRET_KEY = "secret";

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils();
    }

    @Test
    void testGenerateAndValidateToken() {
        UserDetails userDetails = new User("user", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        String token = jwtUtils.generateToken(userDetails);

        assertNotNull(token);
        assertTrue(jwtUtils.validateToken(token, userDetails));
    }

    @Test
    void testExtractUsername() {
        String username = "user";
        String token = Jwts.builder().setClaims(new HashMap<>()).setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();

        assertEquals(username, jwtUtils.extractUsername(token));
    }

    @Test
    void testTokenExpiration() {
        UserDetails userDetails = new User("user", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        String token = jwtUtils.generateToken(userDetails);
        assertFalse(jwtUtils.isTokenExpired(token));

        String expiredToken = Jwts.builder().setClaims(new HashMap<>()).setSubject("user")
                .setIssuedAt(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24))
                .setExpiration(new Date(System.currentTimeMillis() - 1000 * 60 * 60))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();


        assertThrows(ExpiredJwtException.class, () -> jwtUtils.isTokenExpired(expiredToken));
    }

}
