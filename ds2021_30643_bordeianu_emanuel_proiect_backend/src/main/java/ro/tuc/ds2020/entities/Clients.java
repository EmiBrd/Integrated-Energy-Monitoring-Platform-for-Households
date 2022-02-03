package ro.tuc.ds2020.entities;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Clients extends Users {

    @NotBlank(message = "name cannot be empty")
    private String name;
    @NotNull(message = "birthDate cannot be null")
    @DateTimeFormat(pattern = "MM/DD/YYYY")
    //private LocalDateTime birthDate;
    private Date birthDate;
    @NotBlank(message = "address cannot be empty")
    private String address;

    @Builder
    public Clients(Long id, String name, Date birthDate, String address, String email,
                   String username, String password, Integer active)
    {
        super(id, email,username,password, active);
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
    }

}



