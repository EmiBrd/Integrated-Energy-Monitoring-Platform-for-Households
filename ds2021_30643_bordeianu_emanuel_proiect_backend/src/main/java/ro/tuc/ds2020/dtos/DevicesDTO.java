package ro.tuc.ds2020.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DevicesDTO {

    private Long id;
    private String description;
    private String addressLocation;
    private Double maxEnerCons;
    private Double avgBaselineEnerCons;

}
