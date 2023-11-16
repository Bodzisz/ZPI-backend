package zpi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zpi.dto.CommentDto;
import zpi.dto.NewCommentDto;
import zpi.service.CommentService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentsController {

    private final CommentService commentService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody NewCommentDto newCommentDto) {
        return ResponseEntity.ok(commentService.createComment(newCommentDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentsById(@PathVariable Integer id) {
        return ResponseEntity.ok(commentService.getCommentById(id));
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments() {
        return ResponseEntity.ok(commentService.getAllComments());
    }

    @GetMapping("/attraction/{attractionId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsByAttractionId(@PathVariable Integer attractionId) {
        return ResponseEntity.ok(commentService.getAllCommentsByAttractionId(attractionId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Integer id, @RequestBody NewCommentDto newCommentDto) {
        return ResponseEntity.ok(commentService.updateComment(id, newCommentDto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }
}
