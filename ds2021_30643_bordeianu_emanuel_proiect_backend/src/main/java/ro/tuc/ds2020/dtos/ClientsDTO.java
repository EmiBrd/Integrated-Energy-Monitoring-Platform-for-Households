package ro.tuc.ds2020.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientsDTO {

    Long id;
    //@NotBlank(message = "name cannot be empty")
    private String name;
    //@NotNull(message = "birthDate cannot be null")
    //@DateTimeFormat(pattern = "MM/DD/YYYY")
    //private LocalDateTime birthDate;
    private Date birthDate;
    //@NotBlank(message = "address cannot be empty")
    private String address;
    private String email;
    private String username;
    private String password;
    private Integer active;

}
