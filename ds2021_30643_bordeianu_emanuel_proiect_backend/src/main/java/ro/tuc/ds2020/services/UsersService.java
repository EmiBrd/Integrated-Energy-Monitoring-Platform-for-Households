package ro.tuc.ds2020.services;

import org.springframework.stereotype.Component;
import ro.tuc.ds2020.entities.Users;
import java.util.List;

@Component
public interface UsersService {

    Users findByUsername(String username);
    List<Users> findAll();
    Users save(Users dto);
    Users logOut(Long id);

}

