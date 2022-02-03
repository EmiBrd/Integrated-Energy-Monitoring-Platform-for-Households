package ro.tuc.ds2020.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2020.entities.Devices;
import ro.tuc.ds2020.entities.Sensors;
import java.util.List;

@Repository
public interface SensorsRepository extends CrudRepository<Sensors, Long> {
    Sensors findFirstById(Long id);

    Sensors findFirstByDevice(Devices device);
    List<Sensors> findAll();

}