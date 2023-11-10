package zpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zpi.entity.City;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Integer> {
    Optional<City> findByCityNameAndPostalCode(String cityName, String postalCode);
}