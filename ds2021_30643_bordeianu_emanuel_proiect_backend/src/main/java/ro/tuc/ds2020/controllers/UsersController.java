//package ro.tuc.ds2020.controllers;
//
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import ro.tuc.ds2020.entities.Users;
//import ro.tuc.ds2020.services.UsersService;
//
//@RestController
//@RequestMapping("/users")
//@CrossOrigin("*")
//public class UsersController {
//
//    private final UsersService usersService;
//
//    public UsersController(UsersService usersService) {
//        this.usersService = usersService;
//    }
//
//    @ApiOperation(value = "Returns a list of all users")
//    @GetMapping("/findallusers")
//    public ResponseEntity findAllUsers(){
//        System.out.println("\nfindAllUsers din UsersController\n");
//        //return ResponseEntity.status(HttpStatus.OK).body(usersService.findAll().toString() + "\n");
//        return ResponseEntity.status(HttpStatus.OK).body(usersService.findAll());
//    }
//
//    @ApiOperation(value = "Return a user")
//    @GetMapping("/{username}")
//    public ResponseEntity findUserByUsername(@ApiParam(value = "Requires a username for a user") @PathVariable String username){
//        return ResponseEntity.status(HttpStatus.OK).body(usersService.findByUsername(username));
//    }
//
//    @ApiOperation(value = "It will add a user")
//    @PostMapping
//    public ResponseEntity saveNewUtilizator(@ApiParam(value = "Requires a new user")@RequestBody Users user){
//        return ResponseEntity.status(HttpStatus.OK).body(usersService.save(user));
//    }
//
//}
//
//
