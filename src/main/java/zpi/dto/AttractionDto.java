package zpi.dto;

import lombok.Data;
import zpi.entity.Attraction;
import zpi.entity.AttractionType;
import zpi.entity.City;
import zpi.entity.District;

@Data
public class AttractionDto {
    private Integer id;
    private String district;
    private String city;
    private String attractionType;
    private String title;
    private String description;
    private byte[] picture;
    private Float xCoordinate;
    private Float yCoordinate;

    public AttractionDto(Attraction attraction) {
        this.id = attraction.getId();
        this.title = attraction.getTitle();
        this.description = attraction.getDescription();
        this.picture = attraction.getPicture();
        this.xCoordinate = attraction.getXCoordinate();
        this.yCoordinate = attraction.getYCoordinate();
        City city = attraction.getCity();
        if (city != null) {
            this.city = city.getCityName();
        }
        District district = attraction
                .getDistrict();
        if (district != null) {
            this.district = district
                    .getDistrictName();
        }
        AttractionType attractionType = attraction
                .getAttractionType();
        if (attractionType != null) {
            this.attractionType = attractionType
                    .getAttractionType();
        }
    }

}
