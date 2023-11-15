package zpi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import zpi.dto.NewRatingDto;
import zpi.dto.RatingDto;
import zpi.service.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ratings")
public class RatingController {

    private final RatingService ratingService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<RatingDto> createRating(@RequestBody NewRatingDto newRatingDto) {
        return ResponseEntity.ok(ratingService.createRating(newRatingDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingDto> getRatingsById(@PathVariable Integer id) {
        return ResponseEntity.ok(ratingService.getRatingById(id));
    }

    @GetMapping
    public ResponseEntity<List<RatingDto>> getAllRatings() {
        return ResponseEntity.ok(ratingService.getAllRatings());
    }

    @GetMapping("/attraction/{attractionId}")
    public ResponseEntity<List<RatingDto>> getAllRatingsByAttractionId(@PathVariable Integer attractionId) {
        return ResponseEntity.ok(ratingService.getAllRatingsByAttractionId(attractionId));
    }

    @GetMapping("/attraction/{attractionId}/attractionRating")
    public ResponseEntity<Double> getRateForAttractionId(@PathVariable Integer attractionId) {
        return ResponseEntity.ok(ratingService.getRateForAttraction(attractionId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<RatingDto> updateRating(@PathVariable Integer id, @RequestBody NewRatingDto newRatingDto) {
        return ResponseEntity.ok(ratingService.updateRating(id, newRatingDto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable Integer id) {
        ratingService.deleteRating(id);
        return ResponseEntity.ok().build();
    }
}
