package zpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zpi.entity.AttractionType;

import java.util.Optional;

public interface AttractionTypeRepository extends JpaRepository<AttractionType, Integer> {
    Optional<AttractionType> findByAttractionType(String attractionType);
}
