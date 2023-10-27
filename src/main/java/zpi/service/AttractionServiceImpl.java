package zpi.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zpi.entity.Attraction;
import zpi.repository.AttractionRepository;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

    private final AttractionRepository attractionRepository;

    @Override
    public Attraction saveAttraction(Attraction attraction) {
        return attractionRepository.save(attraction);
    }

    @Override
    public List<Attraction> fetchAttractionList() {
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
    public Attraction findAttractionById(Integer attractionId) {
        return attractionRepository.findById(attractionId)
                .orElseThrow(() -> new EntityNotFoundException("Attraction not found for ID: " + attractionId));
    }

    @Override
    public List<Attraction> getAttractionList(List<String> cities, List<String> districts, List<String> types) {

        Predicate<Attraction> cityPredicate = attraction ->
                (cities == null || cities.isEmpty()) || cities.contains(attraction.getCity().getCityName());

        Predicate<Attraction> districtPredicate = attraction ->
                (districts == null || districts.isEmpty()) || districts.contains(attraction.getDistrict().getDistrictName());

        Predicate<Attraction> typePredicate = attraction ->
                (types == null || types.isEmpty()) || types.contains(attraction.getAttractionType().getAttractionType());

        Predicate<Attraction> combinedPredicate = cityPredicate.and(districtPredicate).and(typePredicate);

        return attractionRepository.findAll()
                .stream()
                .filter(combinedPredicate)
                .collect(Collectors.toList());
    }

}
