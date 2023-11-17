package zpi.dto;

import lombok.Data;
import zpi.entity.Rating;

import java.time.LocalDateTime;

@Data
public class RatingDto {
    private Integer id;
    private String username;
    private Integer rating;
    private LocalDateTime createdAt;

    public RatingDto(Rating rating) {
        this.id = rating.getId();
        this.username = rating.getUser().getUsername();
        this.rating = rating.getRating();
        this.createdAt = rating.getCreatedAt();
    }
}
