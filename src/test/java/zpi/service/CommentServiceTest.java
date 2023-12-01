package zpi.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import zpi.dto.NewCommentDto;
import zpi.entity.Attraction;
import zpi.entity.Comment;
import zpi.entity.User;
import zpi.repository.AttractionRepository;
import zpi.repository.CommentsRepository;
import zpi.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class CommentServiceTest {

    @Mock
    private CommentsRepository commentRepository;
    @Mock
    private AttractionRepository attractionRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCommentWithValidData() {
        NewCommentDto newCommentDto = new NewCommentDto("login", 1, "Nice place!");
        User user = new User();
        Attraction attraction = new Attraction();
        Comment comment = new Comment(user, attraction, "Nice place!", LocalDateTime.now());

        when(userRepository.findByLogin("login")).thenReturn(Optional.of(user));
        when(attractionRepository.findById(1)).thenReturn(Optional.of(attraction));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        assertDoesNotThrow(() -> commentService.createComment(newCommentDto));
    }

    @Test
    void testGetCommentByIdFound() {
        int commentId = 1;
        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setUser(new User());

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        assertDoesNotThrow(() -> commentService.getCommentById(commentId));
    }

    @Test
    void testGetCommentByIdNotFound() {
        int commentId = 1;
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> commentService.getCommentById(commentId));
    }

}
