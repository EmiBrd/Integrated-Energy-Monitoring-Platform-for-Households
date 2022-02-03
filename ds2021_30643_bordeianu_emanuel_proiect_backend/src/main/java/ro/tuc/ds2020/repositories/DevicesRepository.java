package ro.tuc.ds2020.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2020.entities.Clients;
import ro.tuc.ds2020.entities.Devices;
import java.util.List;

@Repository
public interface DevicesRepository extends CrudRepository<Devices, Long> {
    Devices findFirstById(Long id);

    List<Devices> findAllByClientId(Long idClient);

    List<Devices> findAll();
}
