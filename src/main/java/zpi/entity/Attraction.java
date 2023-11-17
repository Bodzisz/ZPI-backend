package zpi.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "attractions")
public class Attraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attraction_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "district_id", referencedColumnName = "district_id")
    private District district;

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "city_id")
    private City city;

    @ManyToOne
    @NotNull(message = "AttractionType cannot be null")
    @JoinColumn(name = "type_id", referencedColumnName = "type_id")
    private AttractionType attractionType;

    @NotBlank(message = "Title cannot be blank")
    @Column(name = "title", nullable = false)
    private String title;

    @NotBlank(message = "Description cannot be blank")
    @Column(name = "description")
    private String description;

    @Column(name = "picture", columnDefinition = "BYTEA")
    private byte[] picture;

    @Column(name = "x_coordinate")
    private Float xCoordinate;

    @Column(name = "y_coordinate")
    private Float yCoordinate;

    public Attraction() {
    }

    public Attraction(District district, City city, String title, AttractionType attractionType, String description, byte[] picture, Float xCoordinate, Float yCoordinate) {
        this.district = district;
        this.city = city;
        this.title = title;
        this.attractionType = attractionType;
        this.description = description;
        this.picture = picture;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public Attraction(Integer id, District district, City city, String title, AttractionType attractionType, String description, byte[] picture, Float xCoordinate, Float yCoordinate) {
        this.id = id;
        this.district = district;
        this.city = city;
        this.title = title;
        this.attractionType = attractionType;
        this.description = description;
        this.picture = picture;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }
}
