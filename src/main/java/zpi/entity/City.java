package zpi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Integer id;

    @Column(name = "city_name", nullable = false)
    private String cityName;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    public City() {
    }

    public City(String cityName, String postalCode) {
        this.cityName = cityName;
        this.postalCode = postalCode;
    }
}
