package ro.tuc.ds2020.services.impl;

import org.springframework.stereotype.Service;
import ro.tuc.ds2020.entities.Admins;
import ro.tuc.ds2020.repositories.AdminsRepository;
import ro.tuc.ds2020.services.AdminsService;
import java.util.List;

@Service
public class AdminsServiceImpl implements AdminsService {

    private final AdminsRepository adminsRepository;

    public AdminsServiceImpl(AdminsRepository adminsRepository) {
        this.adminsRepository = adminsRepository;
    }

    @Override
    public Admins findById(Long id){
        return adminsRepository.findFirstById(id);
    }

    @Override
    public List<Admins> findAll() {
        return adminsRepository.findAll();
    }

}





