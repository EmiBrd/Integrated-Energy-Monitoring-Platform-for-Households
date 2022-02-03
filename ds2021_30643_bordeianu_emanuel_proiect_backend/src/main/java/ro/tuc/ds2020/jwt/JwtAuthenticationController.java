package ro.tuc.ds2020.jwt;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.entities.Users;
import ro.tuc.ds2020.services.UsersService;
import java.util.Locale;

@RestController
@CrossOrigin("*")
public class JwtAuthenticationController {
    //@Autowired
    private final AuthenticationManager authenticationManager;
    //@Autowired
    private final JwtTokenUtil jwtTokenUtil;
    //@Autowired
    private final JwtUserDetailsService userDetailsService;
    private final UsersService usersService;

    public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
                                       JwtUserDetailsService userDetailsService, UsersService usersService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.usersService = usersService;
    }

    //@RequestMapping(value = "/loginn", method = RequestMethod.POST)
    @PostMapping("/login")
    public ResponseEntity createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        //LoginTokenResponseDTO loginTokenResponseDTO = new LoginTokenResponseDTO();
        Long id;
        String role;
        if(token != null){
            Users user = usersService.findByUsername(authenticationRequest.getUsername());
            System.out.println("user: id = " + user.getId() +", username = " + user.getUsername() +
                    ", password = " + user.getPassword());
            if(user == null){
                System.out.println("user din JtwAuthController e null");
            }
//            loginTokenResponseDTO.setId(user.getId());
//            loginTokenResponseDTO.setRole(user.getClass().getSimpleName().toUpperCase(Locale.ROOT));
//            loginTokenResponseDTO.setToken(token);
            id = user.getId();
            role = user.getClass().getSimpleName().toUpperCase(Locale.ROOT);
        }
        else{
            System.out.println("\ntoken-ul e null\n");
            return null;
        }
        //return ResponseEntity.ok(new JwtResponse(token, loginResponseDTO));
        //return ResponseEntity.ok(new JwtResponse(loginTokenResponseDTO));
        //return ResponseEntity.ok(new JwtResponse(token, id, role));
        return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(token, id, role));
    }


    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}

