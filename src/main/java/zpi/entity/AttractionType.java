package zpi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "attraction_types")
public class AttractionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private Integer id;

    @Column(name = "attraction_type", nullable = false)
    private String attractionType;

    public AttractionType() {
    }

    public AttractionType(String attractionType) {
        this.attractionType = attractionType;
    }
}
