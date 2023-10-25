package zpi.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zpi.entity.Attraction;
import zpi.repository.AttractionRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        Set<Attraction> attractionsSet = new HashSet<>();

        if (cities != null && !cities.isEmpty()) {
            for (String city : cities) {
                attractionsSet.addAll(attractionRepository.findByCityName(city));
            }
        }

        if (districts != null && !districts.isEmpty()) {
            for (String district : districts) {
                attractionsSet.addAll(attractionRepository.findByDistrictName(district));
            }
        }

        if (types != null && !types.isEmpty()) {
            for (String type : types) {
                attractionsSet.addAll(attractionRepository.findByType(type));
            }
        }
        return new ArrayList<>(attractionsSet);
    }


}
