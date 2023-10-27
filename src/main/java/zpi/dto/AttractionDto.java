package zpi.dto;

import lombok.Data;

@Data
public class AttractionDto {
    private Integer id;
    private String district;
    private String city;
    private String attractionType;
    private String title;
    private String description;
    private byte[] picture;

}
