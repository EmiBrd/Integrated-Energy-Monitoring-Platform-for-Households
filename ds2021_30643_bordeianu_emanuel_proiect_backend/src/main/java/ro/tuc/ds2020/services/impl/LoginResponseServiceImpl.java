//package ro.tuc.ds2020.services.impl;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import ro.tuc.ds2020.controllers.handlers.exceptions.model.ApiExceptionResponse;
//import ro.tuc.ds2020.dtos.CredentialsLoginDTO;
//import ro.tuc.ds2020.dtos.LoginResponseDTO;
//import ro.tuc.ds2020.entities.Users;
//import ro.tuc.ds2020.repositories.UsersRepository;
//import ro.tuc.ds2020.services.LoginResponseService;
//import java.util.Collections;
//import java.util.Locale;
//
//@Service
//public class LoginResponseServiceImpl implements LoginResponseService {
//
//    private final UsersRepository usersRepository;
//
//    public LoginResponseServiceImpl(UsersRepository usersRepository) {
//        this.usersRepository = usersRepository;
//    }
//
//    @Override
//    public LoginResponseDTO login1(CredentialsLoginDTO dtoLogin) throws ApiExceptionResponse {
//        Users user1 = usersRepository.findByUsername(dtoLogin.getUsername());
//        System.out.println("\nLoginResponseDTO, user1 = " + user1.getUsername());
//        System.out.println("dtoLogin = " + dtoLogin.getUsername() + "\n");
//
//        if(user1 == null){
//            System.out.println("\nBad credentials or user not found   |   user1 = null\n");
//            throw ApiExceptionResponse.builder().errors(Collections.singletonList("Bad credentials"))
//                    .message("User not found").status(HttpStatus.NOT_FOUND).build();
//        }
//
//        LoginResponseDTO responseDTO;
//        String role = user1.getClass().getSimpleName().toUpperCase(Locale.ROOT);
//        responseDTO = LoginResponseDTO.builder().id(user1.getId()).role(role).build();
//
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        if(encoder.matches(dtoLogin.getPassword(), user1.getPassword())==true) {
//            user1.setActive(1);
//            usersRepository.save(user1);
//            System.out.println("user-ul cu username = " + user1.getUsername() + ", id = " + user1.getId() +
//                    ", s-a logat\n");
//            return responseDTO;
//        }
//        else{
//            System.out.println("dtoLogin NU se potriveste cu datele user-ului !!!");
//            System.out.println("password dtoLogin = " + dtoLogin.getPassword());
//            System.out.println("username dtoLogin = " + dtoLogin.getUsername() + "\n");
//            System.out.println("password user1 = " + user1.getPassword());
//            System.out.println("username user1 = " + user1.getUsername() + "\n");
//        }
//        throw ApiExceptionResponse.builder().errors(Collections.singletonList("Bad credentials"))
//                .message("User NOT found").status(HttpStatus.NOT_FOUND).build();
//    }
//
//}
//
//
//
//
