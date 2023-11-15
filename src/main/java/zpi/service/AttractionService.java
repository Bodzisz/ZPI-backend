package zpi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zpi.dto.AttractionDto;
import zpi.dto.AttractionLocationDto;
import zpi.dto.AttractionPictureDto;
import zpi.entity.Attraction;
import zpi.entity.City;

import java.util.List;
import java.util.Optional;

public interface AttractionService {

    Attraction createAttraction(Attraction attraction);

    Page<AttractionDto> getAllAttractions(Pageable pageable);

    Attraction updateAttraction(Attraction attraction,
                                Integer attractionId);

    void deleteAttractionById(Integer attractionId);

    AttractionDto getAttractionById(Integer attractionId);

    List<AttractionDto> getRandomAttractions(Optional<Integer> size);

    AttractionLocationDto getAttractionLocation(Integer attractionId);

    List<AttractionLocationDto> getAllAttractionsLocations();

    AttractionPictureDto getAttractionPicture(Integer attractionId);

    Page<AttractionDto> getAttractionsWithFilter(List<String> titles, List<String> cities, List<String> districts,
                                                 List<String> types, Pageable pageable);

    double getDistanceToAttraction(Integer attractionId, Float xCoordinate, Float yCoordinate);

    City addCityIfNotExists(String cityName, String postalCode);
}