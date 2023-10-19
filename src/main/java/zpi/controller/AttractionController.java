package zpi.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zpi.entity.Attraction;
import zpi.service.AttractionService;
import zpi.service.AttractionServiceImpl;

import java.util.List;

@RestController
public class AttractionController {
    private final AttractionService attractionService;

    @Autowired
    public AttractionController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }
    @PostMapping("/attractions")
    public Attraction saveAttraction(@RequestBody Attraction attraction){
        return attractionService.saveAttraction(attraction);
    }
    @GetMapping("/attractions")
    public List<Attraction> fetchAttractionList(){
        return attractionService.fetchAttractionList();
    }

    @PutMapping("/attractions/{id}")
    public Attraction updateAttraction(@RequestBody Attraction attraction, @PathVariable("id")Integer attractionId){
        return attractionService.updateAttraction(attraction,attractionId);
    }

    @DeleteMapping("/attractions/{id}")
    public String deleteAttractionById(@PathVariable("id") Integer attractionId){
        attractionService.deleteAttractionById(attractionId);
        return "Deleted Successfully";
    }
}