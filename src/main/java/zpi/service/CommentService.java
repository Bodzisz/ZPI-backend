package zpi.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zpi.dto.CommentDto;
import zpi.dto.NewCommentDto;
import zpi.entity.Attraction;
import zpi.entity.Comment;
import zpi.entity.User;
import zpi.repository.AttractionRepository;
import zpi.repository.CommentsRepository;
import zpi.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentsRepository commentRepository;
    private final AttractionRepository attractionRepository;
    private final UserRepository userRepository;

    public CommentDto createComment(NewCommentDto newCommentDto) {
        User user = userRepository.findByLogin(newCommentDto.login()).orElseThrow(()
                -> new RuntimeException("User not found"));
        Attraction attraction = attractionRepository.findById(newCommentDto.attractionId()).orElseThrow(()
                -> new RuntimeException("Attraction not found"));

        return new CommentDto(commentRepository.save(
                new Comment(user, attraction, newCommentDto.comment(), LocalDateTime.now())));
    }

    public CommentDto getCommentById(Integer id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            return new CommentDto(comment.get());
        } else {
            throw new EntityNotFoundException("Comment not found for ID: " + id);
        }
    }

    public List<CommentDto> getAllComments() {
        return commentRepository.findAll().stream().map(CommentDto::new).toList();
    }

    public List<CommentDto> getAllCommentsByAttractionId(Integer attractionId) {
        return commentRepository.getAllByAttractionId(attractionId).stream().map(CommentDto::new).toList();
    }

    public CommentDto updateComment(Integer id, NewCommentDto newCommentDto) {
        User user = userRepository.findByLogin(newCommentDto.login()).orElseThrow(()
                -> new RuntimeException("User not found"));
        Attraction attraction = attractionRepository.findById(newCommentDto.attractionId()).orElseThrow(()
                -> new RuntimeException("Attraction not found"));
        return new CommentDto(commentRepository.save(
                new Comment(id, user, attraction, newCommentDto.comment(), LocalDateTime.now())));
    }

    public void deleteComment(Integer id) {
        commentRepository.deleteById(id);
    }
}
