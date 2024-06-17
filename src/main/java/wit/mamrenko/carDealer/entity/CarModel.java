package wit.mamrenko.carDealer.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "car_models", uniqueConstraints = {@UniqueConstraint(name = "UniqueId", columnNames = {"id"})})
public class CarModel implements Serializable {
    @Getter
    @Id
    @Expose
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Expose
    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 255)
    @Column(name = "name", nullable = false)
    private String name;

    @Setter
    @Getter
    @Expose
    @Column(name = "variant", nullable = false)
    private String variant;

    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producer_id", nullable = false)
    public Producer producer;

    @Expose
    @SerializedName("producer_name")
    @Setter
    @Transient
    private String producerName;

    @Expose
    @SerializedName("producer_id")
    @Setter
    @Getter
    @Transient
    private Long producerID;

    @Getter
    @Setter
    @OneToMany(mappedBy = "car_model", cascade = CascadeType.ALL)
    private List<Offer> offers;


    public CarModel() {
    }

    public CarModel(String name, String variant, Producer producer) {
        this.name = name;
        this.variant = variant;
        this.producer = producer;
    }
}
