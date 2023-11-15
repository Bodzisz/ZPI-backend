package zpi.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "districts")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "district_id")
    private Integer id;

    @Column(name = "district_name", nullable = false)
    private String districtName;

    public District() {
    }

    public District(String districtName) {
        this.districtName = districtName;
    }
}
