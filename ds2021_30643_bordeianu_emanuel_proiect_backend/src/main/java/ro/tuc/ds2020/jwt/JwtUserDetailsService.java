package ro.tuc.ds2020.jwt;

import java.util.Locale;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.entities.Users;
import ro.tuc.ds2020.repositories.UsersRepository;
import javax.transaction.Transactional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    public JwtUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Client with username = " +username + ", not found in JWTUserDetailsService");
        }
        return User.withUsername(user.getUsername()).password(user.getPassword()).roles(user.getClass().getSimpleName().
                toUpperCase(Locale.ROOT)).build();
    }

}
