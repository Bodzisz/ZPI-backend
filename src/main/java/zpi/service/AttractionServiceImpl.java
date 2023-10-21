package zpi.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zpi.entity.Attraction;
import zpi.repository.AttractionRepository;

import java.util.List;
import java.util.Optional;


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
        Attraction attractionDB
                = attractionRepository.findById(attractionId)
                .orElseThrow(() -> new EntityNotFoundException("Attraction not found for ID: " + attractionId));

        if(attraction.getTitle() != null && !attraction.getTitle().isEmpty()) {
            attractionDB.setTitle(attraction.getTitle());
        }

        if(attraction.getAttractionType() != null) {
            attractionDB.setAttractionType(attraction.getAttractionType());
        }

        if(attraction.getDescription() != null && !attraction.getDescription().isEmpty()) {
            attractionDB.setDescription(attraction.getDescription());
        }

        return attractionRepository.save(attractionDB);
    }

    @Override
    public void deleteAttractionById(Integer attractionId) {
        attractionRepository.deleteById(attractionId);
    }

    @Override
    public Attraction findAttractionById(Integer attractionId) {
        return attractionRepository.findById(attractionId).get();
    }

}
