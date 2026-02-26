package ua.com.foxmined.carrestservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "information")
public class CarInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="objectid")
    private String objectId;

    @Column(name = "\"year\"")
    private Date dateOfManifacture;

    @ManyToOne
    @JoinColumn(name="model_id", nullable=false)
    private CarModel carModel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarInformation that = (CarInformation) o;
        return Objects.equals(id, that.id)
                && Objects.equals(objectId, that.objectId)
                && Objects.equals(dateOfManifacture, that.dateOfManifacture)
                && Objects.equals(carModel, that.carModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, objectId, dateOfManifacture, carModel);
    }

    @Override
    public String toString() {
        return "CarInformation{" +
                "id=" + id +
                ", objectId='" + objectId + '\'' +
                ", dateOfManifacture=" + dateOfManifacture +
                ", carModel=" + carModel +
                '}';
    }
}
