package zpi.attraction.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import zpi.attraction.dao.AttractionDAO;
import zpi.attraction.dao.AttractionDAOImpl;
import zpi.attraction.entity.Attraction;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
@AllArgsConstructor
public class AttractionService {
    private final AttractionDAO attractionDAO;

    public Optional<List<Attraction>> getAllAttractions(){
        return ofNullable(attractionDAO.getAllAttractions());
    }
}
