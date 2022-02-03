package ro.tuc.ds2020.services.impl;

import org.springframework.stereotype.Service;
import ro.tuc.ds2020.entities.Users;
import ro.tuc.ds2020.repositories.UsersRepository;
import ro.tuc.ds2020.services.UsersService;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Users findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    @Override
    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    @Override
    @Transactional
    public Users save(Users dto) {
        return usersRepository.save(dto);
    }

    @Override
    public Users logOut(Long id) {
        Users user= usersRepository.findById(id).get();
        user.setActive(0);
        usersRepository.save(user);
        System.out.println("user-ul cu id = " + id + " s-a delogat");
        return user;
    }

}


