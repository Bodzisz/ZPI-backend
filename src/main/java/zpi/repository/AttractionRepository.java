package zpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zpi.entity.Attraction;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Integer> {
}