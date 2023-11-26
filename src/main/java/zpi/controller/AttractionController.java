package zpi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zpi.dto.AttractionDto;
import zpi.dto.AttractionLocationDto;
import zpi.dto.AttractionPictureDto;
import zpi.dto.NewAttractionDto;
import zpi.entity.AttractionType;
import zpi.entity.City;
import zpi.entity.District;
import zpi.service.AttractionService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attractions")
public class AttractionController {

    private final AttractionService attractionService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<AttractionDto> createAttraction(@Valid @RequestBody NewAttractionDto newAttractionDto) {
        AttractionDto savedAttraction = attractionService.createAttraction(newAttractionDto);
        return new ResponseEntity<>(savedAttraction, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<AttractionDto>> getAllAttractions(Pageable pageable) {
        Page<AttractionDto> attractions = attractionService.getAllAttractions(pageable);
        return new ResponseEntity<>(attractions, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AttractionDto> updateAttraction(@Valid @RequestBody NewAttractionDto newAttractionDto, @PathVariable("id") Integer attractionId) {
        AttractionDto updatedAttraction = attractionService.updateAttraction(newAttractionDto, attractionId);
        return new ResponseEntity<>(updatedAttraction, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAttractionById(@PathVariable("id") Integer attractionId) {
        attractionService.deleteAttractionById(attractionId);

        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttractionDto> getAttractionById(@PathVariable("id") Integer attractionId) {
        AttractionDto attraction = attractionService.getAttractionById(attractionId);
        return new ResponseEntity<>(attraction, HttpStatus.OK);
    }

    @GetMapping("/{id}/location")
    public ResponseEntity<AttractionLocationDto> getAttractionLocationById(@PathVariable("id") Integer attractionId) {
        AttractionLocationDto attraction = attractionService.getAttractionLocation(attractionId);
        return new ResponseEntity<>(attraction, HttpStatus.OK);
    }

    @GetMapping("/random")
    public ResponseEntity<List<AttractionDto>> getRandomAttractions(Optional<Integer> size) {
        return ResponseEntity.ok(attractionService.getRandomAttractions(size));
    }

    @GetMapping("/locations")
    public ResponseEntity<List<AttractionLocationDto>> getAllAttractionsLocations() {
        return ResponseEntity.ok(attractionService.getAllAttractionsLocations());
    }

    @GetMapping("/{id}/picture")
    public ResponseEntity<AttractionPictureDto> getAttractionPicture(@PathVariable("id") Integer attractionId) {
        return ResponseEntity.ok(attractionService.getAttractionPicture(attractionId));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<AttractionDto>> listAttractions(
            @RequestParam(required = false) List<String> titles,
            @RequestParam(required = false) List<String> cities,
            @RequestParam(required = false) List<String> districts,
            @RequestParam(required = false) List<String> types,
            Pageable pageable) {

        Page<AttractionDto> attractions = attractionService
                .getAttractionsWithFilter(titles, cities, districts, types, pageable);

        return attractions.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(attractions, HttpStatus.OK);
    }

    @GetMapping("/{id}/distance")
    public ResponseEntity<Double> getDistance(
            @PathVariable("id") Integer attractionId,
            @RequestParam Float xCoordinate,
            @RequestParam Float yCoordinate) {

        Double distance = attractionService
                .getDistanceToAttraction(attractionId, xCoordinate, yCoordinate);

        return new ResponseEntity<>(distance, HttpStatus.OK);
    }

    @PostMapping("/addCity")
    public ResponseEntity<City> addCity(@RequestParam String cityName,
                                        @RequestParam String postalCode) {
        City city = attractionService.addCityIfNotExists(cityName, postalCode);
        return new ResponseEntity<>(city, HttpStatus.CREATED);
    }

    @PostMapping("/addAttractionType")
    public ResponseEntity<AttractionType> addAttractionType(@RequestParam String typeName) {
        AttractionType attractionType = attractionService.addAttractionTypeIfNotExists(typeName);
        return new ResponseEntity<>(attractionType, HttpStatus.CREATED);
    }

    @PostMapping("/addDistrict")
    public ResponseEntity<District> addDistrict(@RequestParam String districtName) {
        District district = attractionService.addDistrictIfNotExists(districtName);
        return new ResponseEntity<>(district, HttpStatus.CREATED);
    }
}
