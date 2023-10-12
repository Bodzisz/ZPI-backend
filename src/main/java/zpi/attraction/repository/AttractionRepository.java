package zpi.attraction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zpi.attraction.entity.Attraction;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Integer> {
}
