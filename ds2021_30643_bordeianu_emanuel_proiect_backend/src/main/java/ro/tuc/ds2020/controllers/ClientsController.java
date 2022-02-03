package ro.tuc.ds2020.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ApiExceptionResponse;
import ro.tuc.ds2020.dtos.ClientsDTO;
import ro.tuc.ds2020.services.ClientsService;

@RestController
@RequestMapping("/clients")
@CrossOrigin("*")
public class ClientsController {

    private final ClientsService clientsService;

    public ClientsController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @ApiOperation(value = "Returns a list of all clients.")
    @GetMapping("/findallclients")
    public ResponseEntity findAllClients(){
        System.out.println("findAllClients din ClientController");
        //clientsService.findAll().forEach(System.out::println);
        //System.out.println();
        //return ResponseEntity.status(HttpStatus.OK).body(clientsService.findAll().toString() + "\n");
        return ResponseEntity.status(HttpStatus.OK).body(clientsService.findAllDTO());
    }

    @ApiOperation(value = "Return a client")
    @GetMapping("/{id}")
    public ResponseEntity findClientById(@ApiParam(value = "Requires an id for a client")
                                         @PathVariable Long id){
        //return ResponseEntity.status(HttpStatus.OK).body(clientsService.findById(id));
        return ResponseEntity.status(HttpStatus.OK).body(clientsService.findByIdDTO(id));
    }


    @GetMapping("/findclientid/{id}")
    public ResponseEntity findClientsById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(clientsService.findByIdDTO(id));
    }

    @ApiOperation(value = "It will register a client in database")
    @PostMapping("/register")
    public ResponseEntity registerNewClient(@ApiParam(value = "Requires a client")@RequestBody ClientsDTO clientsDTO){
        //return ResponseEntity.status(HttpStatus.OK).body(clientsService.registerClient(client));
        return ResponseEntity.status(HttpStatus.OK).body(clientsService.registerClientDTO(clientsDTO));
    }

    @ApiOperation(value = "It will add a client in database")
    @PostMapping("/insert")
    public ResponseEntity insertNewClient(@ApiParam(value = "Requires a client")@RequestBody ClientsDTO clientsDTO){
        //return ResponseEntity.status(HttpStatus.OK).body(clientsService.insertClient(client));
        return ResponseEntity.status(HttpStatus.OK).body(clientsService.insertClientDTO(clientsDTO));
    }

    @ApiOperation(value = "It will update a client")
    @PutMapping("/update/{id}")
    public ResponseEntity updateClient(@ApiParam(value = "Requires an ID and Client")@PathVariable Long id,
                                       @RequestBody ClientsDTO clientsDTO){
        System.out.println(id);
        //System.out.println(clientsDTO + "  " + clientsDTO.getUsername() + "  " + clientsDTO.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(clientsService.updateClientDTO(id,clientsDTO));
    }

    @ApiOperation(value = "It will delete a client from database")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteClientById(@ApiParam(value = "Requires a client")@PathVariable Long id)
            throws ApiExceptionResponse {
        return ResponseEntity.status(HttpStatus.OK).body(clientsService.deleteClientByIdDTO(id));
    }

}




