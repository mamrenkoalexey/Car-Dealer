package wit.mamrenko.carDealer.entity;

import com.google.gson.annotations.Expose;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "producers", uniqueConstraints = {@UniqueConstraint(name = "UniqueName", columnNames = {"name"})})
public class Producer implements Serializable {


    public Producer() {

    }

    public Producer(List<CarModel> carModels, String name) {
        this.carModels = carModels;
        this.name = name;
    }

    @Getter
    @Id
    @Expose
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @OneToMany(mappedBy = "producer", cascade = CascadeType.ALL)
    private List<CarModel> carModels;


    @Getter
    @Setter
    @Expose
    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, max = 255)
    @Column(name = "name", nullable = false, unique = true)
    public String name;

}
