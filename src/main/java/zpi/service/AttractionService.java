package zpi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zpi.dto.AttractionDto;
import zpi.dto.AttractionLocationDto;
import zpi.entity.Attraction;

import java.util.List;

public interface AttractionService {

    Attraction createAttraction(Attraction attraction);

    Page<AttractionDto> getAllAttractions(Pageable pageable);

    Attraction updateAttraction(Attraction attraction,
                                Integer attractionId);

    void deleteAttractionById(Integer attractionId);

    AttractionDto getAttractionById(Integer attractionId);

    AttractionLocationDto getAttractionLocation(Integer attractionId);

    Page<AttractionDto> getAttractionsWithFilter(List<String> titles, List<String> cities, List<String> districts,
                                                 List<String> types, Pageable pageable);

    double getDistanceToAttraction(Integer attractionId, Float xCoordinate, Float yCoordinate);

}