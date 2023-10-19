package zpi.service;

import zpi.entity.Attraction;

import java.util.List;

public interface AttractionService {

    // Save operation
    Attraction saveAttraction(Attraction attraction);

    // Read operation
    List<Attraction> fetchAttractionList();

    // Update operation
    Attraction updateAttraction(Attraction attraction,
                                Integer attractionId);

    // Delete operation
    void deleteAttractionById(Integer attractionId);
}