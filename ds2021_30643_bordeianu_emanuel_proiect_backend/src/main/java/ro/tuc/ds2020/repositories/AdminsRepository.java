package ro.tuc.ds2020.repositories;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2020.entities.Admins;
import java.util.List;

@Repository
public interface AdminsRepository extends CrudRepository<Admins, Long> {

    Admins findFirstById(Long id);
    Admins findFirstByUsername(String username);
    List<Admins> findAll();

}
