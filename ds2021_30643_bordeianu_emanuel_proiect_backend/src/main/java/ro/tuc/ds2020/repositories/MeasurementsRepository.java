package ro.tuc.ds2020.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2020.entities.Measurements;
import java.util.List;

@Repository
public interface MeasurementsRepository extends CrudRepository<Measurements, Long> {

    Measurements findFirstById(Long id);
    List<Measurements> findAll();

}
