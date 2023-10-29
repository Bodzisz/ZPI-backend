package zpi.dto;

import lombok.Data;
import zpi.entity.Attraction;

@Data
public class AttractionLocationDto {
    private Integer id;
    private String title;
    private Float xCoordinate;
    private Float yCoordinate;

    public AttractionLocationDto(Attraction attraction) {
        this.id = attraction.getId();
        this.title = attraction.getTitle();
        this.xCoordinate = attraction.getXCoordinate();
        this.yCoordinate = attraction.getYCoordinate();
    }
}
