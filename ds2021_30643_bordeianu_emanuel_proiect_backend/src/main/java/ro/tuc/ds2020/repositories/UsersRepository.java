package ro.tuc.ds2020.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2020.entities.Users;
import java.util.List;

@Repository
public interface UsersRepository extends CrudRepository<Users, Long> {

    Users findByUsername(String username);
    Users findByUsernameAndPassword(String userName, String password);
    //@Query("SELECT u.email  FROM User u WHERE u.username=?1")
    //String extractEmail(String username);
    List<Users> findAll();

}

