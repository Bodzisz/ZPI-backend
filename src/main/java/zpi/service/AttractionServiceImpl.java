package zpi.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zpi.dto.AttractionDto;
import zpi.dto.AttractionLocationDto;
import zpi.dto.AttractionPictureDto;
import zpi.dto.NewAttractionDto;
import zpi.entity.Attraction;
import zpi.entity.AttractionType;
import zpi.entity.City;
import zpi.entity.District;
import zpi.repository.AttractionRepository;
import zpi.repository.AttractionTypeRepository;
import zpi.repository.CityRepository;
import zpi.repository.DistrictRepository;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static zpi.Utils.PaginationUtils.convertToPage;

@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

    private static final int DEFAULT_RANDOM_ATTRACTIONS_SIZE = 10;
    private final AttractionRepository attractionRepository;
    private final CityRepository cityRepository;
    private final AttractionTypeRepository attractionTypeRepository;
    private final DistrictRepository districtRepository;

    @Override
    public AttractionDto createAttraction(NewAttractionDto newAttractionDto) {
        City city = addCityIfNotExists(newAttractionDto.cityName(), newAttractionDto.postalCode());
        AttractionType attractionType = addAttractionTypeIfNotExists(newAttractionDto.attractionType());
        District district = addDistrictIfNotExists(newAttractionDto.district());

        return new AttractionDto(attractionRepository.save(new Attraction(district, city, newAttractionDto.title(),
                attractionType, newAttractionDto.description(), Base64.getDecoder().decode(newAttractionDto.picture()),
                newAttractionDto.xCoordinate(), newAttractionDto.yCoordinate())));
    }

    @Override
    public Page<AttractionDto> getAllAttractions(Pageable pageable) {
        return attractionRepository.findAll(pageable).map(AttractionDto::new);
    }

    @Override
    public AttractionDto updateAttraction(NewAttractionDto newAttractionDto, Integer attractionId) {
        final AttractionDto attraction = getAttractionById(attractionId);

        City city = addCityIfNotExists(newAttractionDto.cityName(), newAttractionDto.postalCode());
        AttractionType attractionType = addAttractionTypeIfNotExists(newAttractionDto.attractionType());
        District district = addDistrictIfNotExists(newAttractionDto.district());

        return new AttractionDto(attractionRepository.save(new Attraction(attractionId, district, city, newAttractionDto.title(),
                attractionType, newAttractionDto.description(), newAttractionDto.picture() == null? attraction.getPicture() : Base64.getDecoder().decode(newAttractionDto.picture()),
                newAttractionDto.xCoordinate(), newAttractionDto.yCoordinate())));
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
        Optional<Attraction> attraction =
                attractionRepository.findById(attractionId);
        if (attraction.isPresent()) {
            return new AttractionDto(attraction.get());
        } else {
            throw new EntityNotFoundException
                    ("Attraction not found for ID: " + attractionId);
        }
    }

    @Override
    public List<AttractionDto> getRandomAttractions(Optional<Integer> size) {
        final int randomAttractionsSize = validateSize(size);

        List<Integer> allIds = attractionRepository.getAllIds();
        if (allIds.size() <= randomAttractionsSize) {
            return getAllAttractions();
        }

        Set<Integer> selectedIds = selectRandomIds(randomAttractionsSize, allIds);
        return getAttractionsByIds(selectedIds);
    }

    private int validateSize(Optional<Integer> size) {
        final int randomAttractionsSize = size.orElse(DEFAULT_RANDOM_ATTRACTIONS_SIZE);
        if (randomAttractionsSize <= 0) {
            throw new IllegalArgumentException("Random attractions size must be greater than 0");
        }
        return randomAttractionsSize;
    }

    private List<AttractionDto> getAllAttractions() {
        return attractionRepository.findAll().stream()
                .map(AttractionDto::new)
                .collect(Collectors.toList());
    }

    private Set<Integer> selectRandomIds(int randomAttractionsSize, List<Integer> allIds) {
        Random random = new Random();
        Set<Integer> selectedIds = new HashSet<>();
        while (selectedIds.size() != randomAttractionsSize) {
            int indexToAdd = random.nextInt(allIds.size());
            selectedIds.add(allIds.get(indexToAdd));
            allIds.remove(indexToAdd);
        }
        return selectedIds;
    }

    private List<AttractionDto> getAttractionsByIds(Set<Integer> ids) {
        return attractionRepository.findAllById(ids).stream()
                .map(AttractionDto::new)
                .collect(Collectors.toList());
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
    public AttractionPictureDto getAttractionPicture(Integer attractionId) {
        Attraction attraction = attractionRepository.findById(attractionId)
                .orElseThrow(() -> new EntityNotFoundException("Attraction not found for ID: " + attractionId));
        return new AttractionPictureDto(attraction.getPicture());
    }

    @Override
    public List<AttractionLocationDto> getAllAttractionsLocations() {
        final List<Attraction> allAttractions = attractionRepository.findAll();
        return allAttractions.stream()
                .map(AttractionLocationDto::new)
                .collect(Collectors.toList());
    }


    @Override
    public Page<AttractionDto> getAttractionsWithFilter(List<String> titles,
                                                        List<String> cities,
                                                        List<String> districts,
                                                        List<String> types,
                                                        Pageable pageable) {

        Predicate<Attraction> titlePredicate = attraction ->
                (titles == null || titles.isEmpty()) || titles.stream().anyMatch((title) -> attraction.getTitle().toLowerCase().contains(title.toLowerCase()));

        Predicate<Attraction> cityPredicate = attraction ->
                (cities == null || cities.isEmpty()) || cities.stream().anyMatch((city) -> attraction.getCity().getCityName().toLowerCase().contains(city.toLowerCase()));

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

    public City addCityIfNotExists(String cityName, String postalCode) {
        return cityRepository.findByCityNameAndPostalCode(cityName, postalCode)
                .orElseGet(() -> cityRepository.save(new City(cityName, postalCode)));
    }

    public AttractionType addAttractionTypeIfNotExists(String typeName) {
        return attractionTypeRepository.findByAttractionType(typeName)
                .orElseGet(() -> attractionTypeRepository.save(new AttractionType(typeName)));
    }

    public District addDistrictIfNotExists(String districtName) {
        return districtRepository.findByDistrictName(districtName)
                .orElseGet(() -> districtRepository.save(new District(districtName)));
    }

    @Override
    public List<AttractionType> getAllAttractionTypes() {
        return attractionTypeRepository.findAll();
    }
}
