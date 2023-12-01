package zpi.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zpi.dto.AttractionDto;
import zpi.entity.Attraction;
import zpi.repository.AttractionRepository;
import zpi.repository.CommentsRepository;
import zpi.repository.RatingRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AttractionServiceImplTest {

    @Mock
    private AttractionRepository attractionRepository;
    @Mock
    private RatingService ratingService;
    @Mock
    private CommentsRepository commentsRepository;
    @Mock
    private RatingRepository ratingRepository;
    @Mock
    private Pageable pageable;

    @InjectMocks
    private AttractionServiceImpl attractionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteAttractionById() {
        int attractionId = 1;
        when(attractionRepository.existsById(attractionId)).thenReturn(true);

        assertDoesNotThrow(() -> attractionService.deleteAttractionById(attractionId));
        verify(attractionRepository).deleteById(attractionId);
    }

    @Test
    void testGetAttractionByIdFound() {
        int attractionId = 1;
        Attraction attraction = new Attraction();
        when(attractionRepository.findById(attractionId)).thenReturn(Optional.of(attraction));

        AttractionDto result = attractionService.getAttractionById(attractionId);

        assertNotNull(result);
    }

    @Test
    void testGetAttractionByIdNotFound() {
        int attractionId = 1;
        when(attractionRepository.findById(attractionId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> attractionService.getAttractionById(attractionId));
    }

    @Test
    void testGetDistanceToAttraction() {
        int attractionId = 1;
        Attraction attraction = new Attraction();
        attraction.setXCoordinate(10.0f);
        attraction.setYCoordinate(20.0f);
        when(attractionRepository.findById(attractionId)).thenReturn(Optional.of(attraction));

        double distance = attractionService.getDistanceToAttraction(attractionId, 15.0f, 25.0f);

        assertTrue(distance > 0);
    }

    @Test
    void testGetAllAttractions() {
        when(attractionRepository.findAll(pageable)).thenReturn(Page.empty());

        Page<AttractionDto> result = attractionService.getAllAttractions(pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
