package zpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zpi.entity.Rating;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    List<Rating> getAllByAttractionId(Integer attractionId);
}
