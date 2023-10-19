package zpi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "districts")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "district_id")
    private Integer id;

    @Column(name = "district_name", nullable = false)
    private String districtName;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    public District() {
    }

    public District(String districtName, String city, String postalCode) {
        this.districtName = districtName;
        this.city = city;
        this.postalCode = postalCode;
    }
}
