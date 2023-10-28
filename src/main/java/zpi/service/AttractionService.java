package zpi.service;

import zpi.dto.AttractionDto;
import zpi.entity.Attraction;

import java.util.List;

public interface AttractionService {

    Attraction createAttraction(Attraction attraction);

    List<Attraction> getAllAttractions();

    Attraction updateAttraction(Attraction attraction,
                                Integer attractionId);

    void deleteAttractionById(Integer attractionId);

    Attraction getAttractionById(Integer attractionId);

    AttractionDto getAttractionLocation(Integer attractionId);
    List<Attraction> getAttractionList(List<String> titles,List<String> cities, List<String> districts, List<String> types);

    double getDistanceToAttraction(Integer attractionId, Float xCoordinate, Float yCoordinate);

}