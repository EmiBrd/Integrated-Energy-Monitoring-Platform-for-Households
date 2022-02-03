package ro.tuc.ds2020.services;

import org.springframework.stereotype.Component;
import ro.tuc.ds2020.entities.Admins;
import java.util.List;

@Component
public interface AdminsService {

    Admins findById(Long id);
    List<Admins> findAll();

}

