package ro.tuc.ds2020.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2020.entities.Clients;
import java.util.Date;
import java.util.List;

@Repository
public interface ClientsRepository extends CrudRepository<Clients, Long> {
    Clients findFirstById(Long id);
    Clients findFirstByUsername(String username);
    List<Clients> findAll();

    Clients findClientsByUsernameAndPassword(String username, String password);
}
