package zpi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import zpi.entity.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MyUserDetailsServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private MyUserDetailsService myUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsernameWithExistingUser() {
        String login = "existingUser";
        User user = new User(); // Ustaw odpowiednie dane użytkownika
        when(userService.getByLogin(login)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> {
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(login);
            assertNotNull(userDetails);
        });
    }

    @Test
    void loadUserByUsernameWithNonExistingUser() {
        String login = "nonExistingUser";
        when(userService.getByLogin(login)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            myUserDetailsService.loadUserByUsername(login);
        });
    }
}
