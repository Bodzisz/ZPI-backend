package zpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zpi.entity.District;

import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Integer> {
    Optional<District> findByDistrictName(String districtName);
}
