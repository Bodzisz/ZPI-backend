package zpi.entity;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "attraction_id", referencedColumnName = "attraction_id")
    private Attraction attraction;

    @NotBlank(message = "Comment cannot be blank")
    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public Comment() {
    }

    public Comment(User user, Attraction attraction, String comment, LocalDateTime createdAt) {
        this.user = user;
        this.attraction = attraction;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public Comment(Integer id, User user, Attraction attraction, String comment, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.attraction = attraction;
        this.comment = comment;
        this.createdAt = createdAt;
    }
}
