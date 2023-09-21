package zpi.attraction.dao;

import zpi.attraction.entity.Attraction;

import java.util.List;

public interface AttractionDAO {

    void addAttraction(Attraction attraction);

    Attraction getById(Integer id);

    List<Attraction> getAllAttractions();
}
