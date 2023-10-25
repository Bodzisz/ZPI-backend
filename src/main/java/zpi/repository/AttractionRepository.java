package zpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zpi.entity.Attraction;
import zpi.entity.City;

import java.util.List;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Integer> {
    @Query("SELECT a FROM Attraction a WHERE a.city.cityName = :cityName")
    List<Attraction> findByCityName(@Param("cityName") String cityName);

    @Query("SELECT a FROM Attraction a WHERE a.attractionType.attractionType = :attractionType")
    List<Attraction> findByType(@Param("attractionType") String attractionType);

    @Query("SELECT a FROM Attraction a WHERE a.district.districtName = :districtName")
    List<Attraction> findByDistrictName(@Param("districtName") String districtName);
}