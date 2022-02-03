package ro.tuc.ds2020.controllers;

import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.services.UsersService;

@RestController
@CrossOrigin("*")
public class LoginController {

    private final UsersService usersService;

    public LoginController(UsersService usersService) {
        this.usersService = usersService;
    }

//    @PostMapping("/login/")
//    public ResponseEntity loginReq(@RequestBody CredentialsLoginDTO dto) throws ApiExceptionResponse {
//        return ResponseEntity.status(HttpStatus.OK).body(loginResponseService.login1(dto));
//    }

    @PutMapping("/logout/{id}")
    public ResponseEntity logOut(@ApiParam(value = "Requires an user id")@PathVariable Long id){
        System.out.println("user-ul cu id = " + id + " s-a delogat\n");
        return ResponseEntity.status(HttpStatus.OK).body(usersService.logOut(id));
    }

}


