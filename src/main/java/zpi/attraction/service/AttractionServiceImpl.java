package zpi.attraction.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zpi.attraction.repository.AttractionRepository;
import zpi.attraction.entity.Attraction;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService{

    @Autowired
    private AttractionRepository attractionRepository;
    @Override
    public Attraction saveAttraction(Attraction attraction) {
        return attractionRepository.save(attraction);
    }

    @Override
    public List<Attraction> fetchAttractionList() {
        return (List<Attraction>)attractionRepository.findAll();
    }

    @Override
    public Attraction updateAttraction(Attraction attraction, Integer attractionId) {
        Attraction attractionDB
                = attractionRepository.findById(attractionId)
                .get();

        if (Objects.nonNull(attraction.getTitle())
                && !"".equalsIgnoreCase(
                attraction.getTitle())) {
            attractionDB.setTitle(
                    attraction.getTitle());
        }

        if (Objects.nonNull(attraction.getAttractionType())
                && !"".equalsIgnoreCase(
                attraction.getAttractionType())) {
            attractionDB.setAttractionType(
                    attraction.getAttractionType());
        }

        if (Objects.nonNull(attraction.getDescription())
                && !"".equalsIgnoreCase(
                attraction.getDescription())) {
            attractionDB.setDescription(
                    attraction.getDescription());
        }
        if (Objects.nonNull(attraction.getCity())
                && !"".equalsIgnoreCase(
                attraction.getCity())) {
            attractionDB.setCity(
                    attraction.getCity());
        }
        if (Objects.nonNull(attraction.getPostalCode())
                && !"".equalsIgnoreCase(
                attraction.getPostalCode())) {
            attractionDB.setPostalCode(
                    attraction.getPostalCode());
        }
        return attractionRepository.save(attractionDB);
    }

    @Override
    public void deleteAttractionById(Integer attractionId) {
        attractionRepository.deleteById(attractionId);
    }
}
