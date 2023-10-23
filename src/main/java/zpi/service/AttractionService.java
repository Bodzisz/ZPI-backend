package zpi.service;

import zpi.entity.Attraction;

import java.util.List;

public interface AttractionService {

    Attraction saveAttraction(Attraction attraction);

    List<Attraction> fetchAttractionList();

    Attraction updateAttraction(Attraction attraction,
                                Integer attractionId);

    void deleteAttractionById(Integer attractionId);

    Attraction findAttractionById(Integer attractionId);

}