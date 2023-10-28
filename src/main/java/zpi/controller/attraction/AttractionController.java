package zpi.controller.attraction;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zpi.dto.AttractionDto;
import zpi.entity.Attraction;
import zpi.service.AttractionService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attractions")
public class AttractionController {

    @Autowired
    private ModelMapper modelMapper;

    private final AttractionService attractionService;

    @PostMapping
    public ResponseEntity<Attraction> createAttraction(@Valid @RequestBody Attraction attraction) {
        Attraction savedAttraction = attractionService.createAttraction(attraction);
        return new ResponseEntity<>(savedAttraction, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AttractionDto>> getAllAttractions() {
        List<AttractionDto> attractions =attractionService.getAllAttractions().stream().map(
                attraction -> modelMapper.map(attraction, AttractionDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(attractions, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Attraction> updateAttraction(@Valid @RequestBody Attraction attraction, @PathVariable("id") Integer attractionId) {
        Attraction updatedAttraction = attractionService.updateAttraction(attraction, attractionId);
        return new ResponseEntity<>(updatedAttraction, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAttractionById(@PathVariable("id") Integer attractionId) {
        attractionService.deleteAttractionById(attractionId);

        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttractionDto> getAttractionById(@PathVariable("id") Integer attractionId) {
        Attraction attraction = attractionService.getAttractionById(attractionId);
        AttractionDto attractionResponse = modelMapper.map(attraction, AttractionDto.class);
        return new ResponseEntity<>(attractionResponse, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<AttractionDto>> listAttractions(
            @RequestParam(required = false) List<String> titles,
            @RequestParam(required = false) List<String> cities,
            @RequestParam(required = false) List<String> districts,
            @RequestParam(required = false) List<String> types) {

        List<Attraction> attractions = attractionService.getAttractionList(
                titles,
                cities,
                districts,
                types);
        List<AttractionDto> attractionResponse =attractions.stream().map(
                attraction -> modelMapper.map(attraction, AttractionDto.class)).collect(Collectors.toList());

        return attractionResponse.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(attractionResponse, HttpStatus.OK);
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

}
