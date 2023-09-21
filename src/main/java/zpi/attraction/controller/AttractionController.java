package zpi.attraction.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zpi.attraction.entity.Attraction;
import zpi.attraction.service.AttractionService;

import java.util.List;

@RestController
@AllArgsConstructor
public class AttractionController {
    private final AttractionService attractionService;

    @GetMapping("/attractions/all")
    public ResponseEntity<List<Attraction>> getAllAttractions() {
        return attractionService.getAllAttractions()
                .map(attractions -> new ResponseEntity<>(attractions, HttpStatus.OK))
                .orElse(new ResponseEntity<>(List.of(), HttpStatus.NO_CONTENT));
    }
}
