package ro.tuc.ds2020.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Measurements {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date timestamp;
    private Double energyConsumption;

    @ManyToOne(fetch = FetchType.EAGER)
    private Sensors sensor;

    public Measurements(Long id, Date timestamp, Double energyConsumption){
        this.id = id;
        this.timestamp = timestamp;
        this.energyConsumption = energyConsumption;
    }

}

