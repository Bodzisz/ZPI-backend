package zpi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zpi.entity.Attraction;
import zpi.service.AttractionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attractions")
public class AttractionController {
    private final AttractionService attractionService;

    @PostMapping
    public ResponseEntity<Attraction> saveAttraction(@RequestBody Attraction attraction) {
        Attraction savedAttraction = attractionService.saveAttraction(attraction);
        return new ResponseEntity<>(savedAttraction, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Attraction>> fetchAttractionList() {
        List<Attraction> attractions = attractionService.fetchAttractionList();
        return new ResponseEntity<>(attractions, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Attraction> updateAttraction(@RequestBody Attraction attraction, @PathVariable("id") Integer attractionId) {
        Attraction updatedAttraction = attractionService.updateAttraction(attraction, attractionId);
        return new ResponseEntity<>(updatedAttraction, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAttractionById(@PathVariable("id") Integer attractionId) {
        attractionService.deleteAttractionById(attractionId);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }
}