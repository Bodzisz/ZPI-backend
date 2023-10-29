package zpi.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.springframework.stereotype.Service;
import zpi.dto.AttractionDto;
import zpi.entity.Attraction;
import zpi.repository.AttractionRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

    private final AttractionRepository attractionRepository;

    @Override
    public Attraction createAttraction(Attraction attraction) {
        return attractionRepository.save(attraction);
    }

    @Override
    public List<Attraction> getAllAttractions() {
        return attractionRepository.findAll();
    }

    @Override
    public Attraction updateAttraction(Attraction attraction, Integer attractionId) {
        if (!attractionRepository.existsById(attractionId)) {
            throw new EntityNotFoundException("Attraction not found for ID: " + attractionId);
        }
        attraction.setId(attractionId);
        return attractionRepository.save(attraction);
    }

    @Override
    public void deleteAttractionById(Integer attractionId) {
        if (!attractionRepository.existsById(attractionId)) {
            throw new EntityNotFoundException("Attraction not found for ID: " + attractionId);
        }
        attractionRepository.deleteById(attractionId);
    }

    @Override
    public Attraction getAttractionById(Integer attractionId) {
        Optional<Attraction> attraction = attractionRepository.findById(attractionId);
        if (attraction.isPresent()) {
            return attraction.get();
        } else {
            throw new EntityNotFoundException("Attraction not found for ID: " + attractionId);
        }
    }

    @Override
    public AttractionDto getAttractionLocation(Integer attractionId) {
        return null;
    }


    @Override
    public List<Attraction> getAttractionList(List<String> titles, List<String> cities, List<String> districts, List<String> types) {

        Predicate<Attraction> titlePredicate = attraction ->
                (titles == null || titles.isEmpty()) || titles.contains(attraction.getTitle());

        Predicate<Attraction> cityPredicate = attraction ->
                (cities == null || cities.isEmpty()) || cities.contains(attraction.getCity().getCityName());

        Predicate<Attraction> districtPredicate = attraction ->
                (districts == null || districts.isEmpty()) || districts.contains(attraction.getDistrict().getDistrictName());

        Predicate<Attraction> typePredicate = attraction ->
                (types == null || types.isEmpty()) || types.contains(attraction.getAttractionType().getAttractionType());

        Predicate<Attraction> combinedPredicate = titlePredicate.and(cityPredicate).and(districtPredicate).and(typePredicate);

        return attractionRepository.findAll()
                .stream()
                .filter(combinedPredicate)
                .collect(Collectors.toList());
    }

    @Override
    public double getDistanceToAttraction(Integer attractionId, Float xCoordinate, Float yCoordinate) {
        Attraction attraction = getAttractionById(attractionId);
        Vector2D attractionLocalisation = new Vector2D(attraction.getXCoordinate(), attraction.getYCoordinate());
        Vector2D userLocalisation = new Vector2D(xCoordinate, yCoordinate);

        return convertToKilometers(attractionLocalisation.distance(userLocalisation));
    }

    public double convertToKilometers(double distance) {
        return Math.round(distance * 10000.0) / 100.0;
    }


}
