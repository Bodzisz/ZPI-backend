package zpi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zpi.dto.*;
import zpi.entity.AttractionType;
import zpi.entity.City;
import zpi.entity.District;

import java.util.List;
import java.util.Optional;

public interface AttractionService {

    AttractionDto createAttraction(NewAttractionDto newAttractionDto);

    Page<AttractionDto> getAllAttractions(Pageable pageable);

    AttractionDto updateAttraction(NewAttractionDto newAttractionDto,
                                   Integer attractionId);

    void deleteAttractionById(Integer attractionId);

    AttractionDto getAttractionById(Integer attractionId);

    List<AttractionDto> getRandomAttractions(Optional<Integer> size);

    AttractionLocationDto getAttractionLocation(Integer attractionId);

    List<AttractionLocationDto> getAllAttractionsLocations();

    AttractionPictureDto getAttractionPicture(Integer attractionId);

    Page<AttractionDto> getAttractionsWithFilter(List<String> titles, List<String> cities, List<String> districts,
                                                 List<String> types, boolean sortedByName, Pageable pageable);

    double getDistanceToAttraction(Integer attractionId, Float xCoordinate, Float yCoordinate);

    City addCityIfNotExists(String cityName, String postalCode);

    AttractionType addAttractionTypeIfNotExists(String typeName);

    District addDistrictIfNotExists(String districtName);

    List<AttractionType> getAllAttractionTypes();

    AttractionWithRatingDto getAttractionWithRatingById(Integer attractionId);

    Page<AttractionWithRatingDto> getAttractionsWithFilterSortedByRating(List<String> titles,
                                                                         List<String> cities,
                                                                         List<String> districts,
                                                                         List<String> types,
                                                                         Pageable pageable);
}
