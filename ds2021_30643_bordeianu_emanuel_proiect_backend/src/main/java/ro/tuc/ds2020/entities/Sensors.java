package ro.tuc.ds2020.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Sensors {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "description cannot be empty")
    private String description;
    @NotNull(message = "maxValue cannot be null")
    private Double maxValue;

    @OneToOne
    //@JoinColumn(name = "device_id")
    private Devices device;

    public Sensors(Long id, String description, Double maxValue)
    {
        this.id = id;
        this.description = description;
        this.maxValue = maxValue;
    }

}

