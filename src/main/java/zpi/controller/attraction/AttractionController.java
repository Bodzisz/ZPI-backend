package zpi.controller.attraction;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zpi.entity.Attraction;
import zpi.service.AttractionService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attractions")
public class AttractionController {

    private final AttractionService attractionService;

    @PostMapping
    public ResponseEntity<Attraction> saveAttraction(@Valid @RequestBody Attraction attraction) {
        Attraction savedAttraction = attractionService.saveAttraction(attraction);
        return new ResponseEntity<>(savedAttraction, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Attraction>> fetchAttractionList() {
        List<Attraction> attractions = attractionService.fetchAttractionList();
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
    public ResponseEntity<Attraction> findAttractionById(@PathVariable("id") Integer attractionId) {
        Attraction attraction = attractionService.findAttractionById(attractionId);
        return new ResponseEntity<>(attraction, HttpStatus.OK);
    }
}