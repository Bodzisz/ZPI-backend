package zpi.attraction.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "attractions")
public class Attraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "district_id")
    private Integer districtId;

    @Column(name = "district_name")
    private String districtName;

    @Column(name = "title")
    private String title;

    @Column(name = "attraction_type")
    private String attractionType;

    @Column(name = "description")
    private String description;

    @Column(name = "picture", columnDefinition = "BYTEA")
    private byte[] picture;

    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "x_coordinate")
    private Float xCooridnate;

    @Column(name = "y_coordinate")
    private Float yCooridnate;

    public Attraction() {
    }

    public Attraction(Integer districtId, String districtName, String title, String attractionType, String description, String city, String postalCode, Float xCooridnate, Float yCooridnate) {
        this.districtId = districtId;
        this.districtName = districtName;
        this.title = title;
        this.attractionType = attractionType;
        this.description = description;
        this.city = city;
        this.postalCode = postalCode;
        this.xCooridnate = xCooridnate;
        this.yCooridnate = yCooridnate;
    }
}
