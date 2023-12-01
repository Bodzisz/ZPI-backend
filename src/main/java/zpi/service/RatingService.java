package zpi.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zpi.dto.NewRatingDto;
import zpi.dto.RatingDto;
import zpi.entity.Attraction;
import zpi.entity.Rating;
import zpi.entity.User;
import zpi.repository.AttractionRepository;
import zpi.repository.RatingRepository;
import zpi.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final AttractionRepository attractionRepository;
    private final UserRepository userRepository;

    public RatingDto createRating(NewRatingDto newRatingDto) {
        User user = userRepository.findByLogin(newRatingDto.login()).orElseThrow(()
                -> new RuntimeException("User not found"));
        Attraction attraction = attractionRepository.findById(newRatingDto.attractionId()).orElseThrow(()
                -> new RuntimeException("Attraction not found"));

        if (isRatingValid(newRatingDto.rating())) {
            throw new IllegalArgumentException("Ranting must be between 1 and 5");
        }

        Optional<Rating> optionalRating = ratingRepository.getRatingByAttractionIdAndUserId(attraction.getId(), user.getId());
        return optionalRating.isPresent() ? updateRating(optionalRating.get().getId(), newRatingDto) :
                new RatingDto(ratingRepository.save(
                        new Rating(user, attraction, newRatingDto.rating(), LocalDateTime.now())));
    }

    public RatingDto getRatingById(Integer id) {
        Optional<Rating> rating = ratingRepository.findById(id);
        if (rating.isPresent()) {
            return new RatingDto(rating.get());
        } else {
            throw new EntityNotFoundException("Rating not found for ID: " + id);
        }
    }

    public List<RatingDto> getAllRatings() {
        return ratingRepository.findAll().stream().map(RatingDto::new).toList();
    }

    public List<RatingDto> getAllRatingsByAttractionId(Integer attractionId) {
        return ratingRepository.getAllByAttractionId(attractionId).stream().map(RatingDto::new).toList();
    }

    public double getAvgRateForAttraction(Integer attractionId) {
        return ratingRepository.getAllByAttractionId(attractionId)
                .stream().map(Rating::getRating)
                .mapToDouble(Integer::doubleValue)
                .average()
                .orElse(0.0);
    }


    public RatingDto updateRating(Integer id, NewRatingDto newRatingDto) {
        if (!ratingRepository.existsById(id)) {
            throw new EntityNotFoundException("Rating not found for ID: " + id);
        } else if (isRatingValid(newRatingDto.rating())) {
            throw new IllegalArgumentException("Ranting must be greater than 0 and smaller than 6!");
        }

        User user = userRepository.findByLogin(newRatingDto.login()).orElseThrow(()
                -> new RuntimeException("User not found"));
        Attraction attraction = attractionRepository.findById(newRatingDto.attractionId()).orElseThrow(()
                -> new RuntimeException("Attraction not found"));

        return new RatingDto(ratingRepository.save(
                new Rating(id, user, attraction, newRatingDto.rating(), LocalDateTime.now())));
    }

    public void deleteRating(Integer id) {
        ratingRepository.deleteById(id);
    }

    public boolean isRatingValid(Integer rating) {
        return rating < 1 || rating > 5;
    }
}
