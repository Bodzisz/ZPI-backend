package zpi.dto;

import lombok.Data;
import zpi.entity.*;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Integer id;
    private String username;
    private String comment;
    private LocalDateTime createdAt;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.username = comment.getUser().getUsername();
        this.comment = comment.getComment();
        this.createdAt = comment.getCreatedAt();
    }
}
