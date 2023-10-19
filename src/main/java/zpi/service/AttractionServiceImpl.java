package zpi.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zpi.repository.AttractionRepository;
import zpi.entity.Attraction;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class AttractionServiceImpl implements AttractionService{

    private final AttractionRepository attractionRepository;

    @Autowired
    public AttractionServiceImpl(AttractionRepository attractionRepository) {
        this.attractionRepository=attractionRepository;
    }
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

        if (Objects.nonNull(attraction.getAttractionType())) {
            attractionDB.setAttractionType(attraction.getAttractionType());
        }


        if (Objects.nonNull(attraction.getDescription())
                && !"".equalsIgnoreCase(
                attraction.getDescription())) {
            attractionDB.setDescription(
                    attraction.getDescription());
        }
        if (Objects.nonNull(attraction.getDistrict().getCity())
                && !"".equalsIgnoreCase(attraction.getDistrict().getCity())) {
            attractionDB.getDistrict().setCity(attraction.getDistrict().getCity());
        }

        if (Objects.nonNull(attraction.getDistrict().getPostalCode())
                && !"".equalsIgnoreCase(attraction.getDistrict().getPostalCode())) {
            attractionDB.getDistrict().setPostalCode(attraction.getDistrict().getPostalCode());
        }

        return attractionRepository.save(attractionDB);
    }

    @Override
    public void deleteAttractionById(Integer attractionId) {
        attractionRepository.deleteById(attractionId);
    }
}
