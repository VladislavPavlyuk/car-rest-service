package ua.com.foxmined.carrestservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "modelcategory")
public class CarModelCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    private CarCategory carCategory;

    @ManyToOne
    @JoinColumn(name="model_id", nullable=false)
    private CarModel carModel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarModelCategory that = (CarModelCategory) o;
        return Objects.equals(id, that.id)
                && Objects.equals(carCategory, that.carCategory)
                && Objects.equals(carModel, that.carModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, carCategory, carModel);
    }

    @Override
    public String toString() {
        return "CarModelCategory{" +
                "id=" + id +
                ", carCategory=" + carCategory +
                ", carModel=" + carModel +
                '}';
    }
}
