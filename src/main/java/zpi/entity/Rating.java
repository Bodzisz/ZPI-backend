package zpi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "attraction_id")
    private Attraction attraction;
    @Column(name = "rating", nullable = false)
    private Integer rating;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Rating() {
    }

    public Rating(User user, Attraction attraction, Integer rating, LocalDateTime createdAt) {
        this.user = user;
        this.attraction = attraction;
        this.rating = rating;
        this.createdAt = createdAt;
    }

    public Rating(Integer id, User user, Attraction attraction, Integer rating, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.attraction = attraction;
        this.rating = rating;
        this.createdAt = createdAt;
    }
}
