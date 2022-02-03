package ro.tuc.ds2020.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementsDTO {

    private Long id;
    private Date timestamp;
    private Double energyConsumption;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasurementsDTO that = (MeasurementsDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(timestamp, that.timestamp) && Objects.equals(energyConsumption, that.energyConsumption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timestamp, energyConsumption);
    }

}
