package zpi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import zpi.dto.NewUserDto;
import zpi.entity.User;
import zpi.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserFound() {
        int userId = 1;
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getUser(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
    }

    @Test
    void testGetUserNotFound() {
        int userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> userService.getUser(userId));
    }

    @Test
    void testAddUserWithValidData() {
        NewUserDto newUserDto = new NewUserDto("John", "Doe", "johndoe", "password123","ADMIN");
        User user = new User(newUserDto.firstName(), newUserDto.lastName(), newUserDto.login(), newUserDto.password(), "USER");

        when(passwordEncoder.encode(anyString())).thenReturn("password123");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.addUser(newUserDto);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("password123", result.getPassword());
    }

    @Test
    void testAddUserWithInvalidData() {
        NewUserDto newUserDto = new NewUserDto("", "", "jd", "12","ADMIN");

        assertThrows(ResponseStatusException.class, () -> userService.addUser(newUserDto));
    }

}
