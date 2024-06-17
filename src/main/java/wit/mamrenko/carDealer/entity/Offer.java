package wit.mamrenko.carDealer.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Entity
@Table(name = "offers", uniqueConstraints = {@UniqueConstraint(name = "UniqueId", columnNames = {"id"})})
public class Offer implements Serializable {

    public Offer() {
    }

    public Offer(String vin, Integer year, String color, Integer price, CarModel car_model) {
        this.vin = vin;
        this.year = year;
        this.color = color;
        this.price = price;
        this.car_model = car_model;
    }


    @Getter
    @Id
    @Expose
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Getter
    @Setter
    @Expose
    @Column(name = "vin", nullable = false)
    private String vin;


    @Getter
    @Setter
    @Expose
    @Column(name = "year", nullable = false)
    private Integer year;


    @Getter
    @Setter
    @Expose
    @Column(name = "color", nullable = false)
    private String color;

    @Getter
    @Setter
    @Expose
    @Column(name = "price", nullable = false)
    private Integer price;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_model_id")
    public CarModel car_model;

    @Expose
    @SerializedName("car_model_name")
    @Setter
    @Transient
    private String carModelName;

    @Expose
    @SerializedName("car_model_id")
    @Setter
    @Getter
    @Transient
    private Long carModelId;

}
