package zpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zpi.entity.Rating;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    List<Rating> getAllByAttractionId(Integer attractionId);

    Optional<Rating> getRatingByAttractionIdAndUserId(Integer attractionId, Integer userId);

    void deleteAllByAttractionId(Integer attractionId);

}
