package zpi.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zpi.dto.AttractionDto;
import zpi.dto.AttractionLocationDto;
import zpi.entity.Attraction;
import zpi.repository.AttractionRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static zpi.Utils.PaginationUtils.convertToPage;

@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

    private final AttractionRepository attractionRepository;

    @Override
    public Attraction createAttraction(Attraction attraction) {
        return attractionRepository.save(attraction);
    }

    @Override
    public Page<AttractionDto> getAllAttractions(Pageable pageable) {
        return attractionRepository.findAll(pageable).map(AttractionDto::new);
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
    public AttractionDto getAttractionById(Integer attractionId) {
        Optional<Attraction> attraction = attractionRepository.findById(attractionId);
        if (attraction.isPresent()) {
            return new AttractionDto(attraction.get());
        } else {
            throw new EntityNotFoundException("Attraction not found for ID: " + attractionId);
        }
    }

    @Override
    public AttractionLocationDto getAttractionLocation(Integer attractionId) {
        Optional<Attraction> attraction = attractionRepository.findById(attractionId);
        if (attraction.isPresent()) {
            return new AttractionLocationDto(attraction.get());
        } else {
            throw new EntityNotFoundException("Attraction not found for ID: " + attractionId);
        }
    }


    @Override
    public Page<AttractionDto> getAttractionsWithFilter(List<String> titles,
                                                        List<String> cities,
                                                        List<String> districts,
                                                        List<String> types,
                                                        Pageable pageable) {

        Predicate<Attraction> titlePredicate = attraction ->
                (titles == null || titles.isEmpty()) || titles.contains(attraction.getTitle());

        Predicate<Attraction> cityPredicate = attraction ->
                (cities == null || cities.isEmpty()) || cities.contains(attraction.getCity().getCityName());

        Predicate<Attraction> districtPredicate = attraction ->
                (districts == null || districts.isEmpty()) || districts.contains(attraction.getDistrict().getDistrictName());

        Predicate<Attraction> typePredicate = attraction ->
                (types == null || types.isEmpty()) || types.contains(attraction.getAttractionType().getAttractionType());

        Predicate<Attraction> combinedPredicate = titlePredicate.and(cityPredicate).and(districtPredicate).and(typePredicate);

        return convertToPage(attractionRepository.findAll()
                .stream()
                .filter(combinedPredicate)
                .collect(Collectors.toList()), pageable)
                .map(AttractionDto::new);
    }

    @Override
    public double getDistanceToAttraction(Integer attractionId, Float xCoordinate, Float yCoordinate) {
        Optional<Attraction> attraction = attractionRepository.findById(attractionId);
        if (attraction.isPresent()) {
            Vector2D attractionLocalisation = new Vector2D(attraction.get().getXCoordinate(),
                    attraction.get().getYCoordinate());
            Vector2D userLocalisation = new Vector2D(xCoordinate, yCoordinate);

            return convertToKilometers(attractionLocalisation.distance(userLocalisation));
        } else {
            throw new EntityNotFoundException("Attraction not found for ID: " + attractionId);
        }
    }

    public double convertToKilometers(double distance) {
        return Math.round(distance * 10000.0) / 100.0;
    }
}
