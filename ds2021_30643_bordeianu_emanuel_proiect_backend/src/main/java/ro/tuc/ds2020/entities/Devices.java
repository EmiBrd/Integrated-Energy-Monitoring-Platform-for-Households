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
public class Devices {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "description cannot be empty")
    private String description;
    @NotBlank(message = "addressLocation cannot be empty")
    private String addressLocation;
    @NotNull(message = "maxEnerCons cannot be null")
    private Double maxEnerCons;
    @NotNull(message = "avgBaselineEnerCons cannot be null")
    private Double avgBaselineEnerCons;

    // new
    @ManyToOne(fetch = FetchType.EAGER)
    private Clients client;

    @Builder
    public Devices(Long id, String description, String addressLocation, Double maxEnerCons,
                   Double avgBaselineEnerCons)
    {
        this.id = id;
        this.description = description;
        this.addressLocation = addressLocation;
        this.maxEnerCons = maxEnerCons;
        this.avgBaselineEnerCons = avgBaselineEnerCons;
    }

}

