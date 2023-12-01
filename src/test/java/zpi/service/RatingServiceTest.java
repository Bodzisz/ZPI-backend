package zpi.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import zpi.dto.NewRatingDto;
import zpi.entity.Attraction;
import zpi.entity.Rating;
import zpi.entity.User;
import zpi.repository.AttractionRepository;
import zpi.repository.RatingRepository;
import zpi.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;
    @Mock
    private AttractionRepository attractionRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RatingService ratingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRatingWithValidData() {
        NewRatingDto newRatingDto = new NewRatingDto("login", 1, 5);
        User user = new User();
        Attraction attraction = new Attraction();
        Rating rating = new Rating(user, attraction, 5, LocalDateTime.now());

        when(userRepository.findByLogin("login")).thenReturn(Optional.of(user));
        when(attractionRepository.findById(1)).thenReturn(Optional.of(attraction));
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        assertDoesNotThrow(() -> {
            ratingService.createRating(newRatingDto);
        });
    }

    @Test
    void testCreateRatingWithInvalidRating() {
        NewRatingDto newRatingDto = new NewRatingDto("login", 1, 6);

        assertThrows(RuntimeException.class, () -> ratingService.createRating(newRatingDto));
    }

    @Test
    void testGetRatingByIdFound() {
        int ratingId = 1;
        Rating rating = new Rating();
        rating.setId(ratingId);
        rating.setUser(new User());


        when(ratingRepository.findById(ratingId)).thenReturn(Optional.of(rating));

        assertDoesNotThrow(() -> {
            ratingService.getRatingById(ratingId);
        });
    }

    @Test
    void testGetRatingByIdNotFound() {
        int ratingId = 1;
        when(ratingRepository.findById(ratingId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> ratingService.getRatingById(ratingId));
    }

}
