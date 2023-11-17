package zpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zpi.entity.Attraction;

import java.util.List;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Integer> {

    @Query(value = "SELECT a.id FROM Attraction a")
    List<Integer> getAllIds();
}
