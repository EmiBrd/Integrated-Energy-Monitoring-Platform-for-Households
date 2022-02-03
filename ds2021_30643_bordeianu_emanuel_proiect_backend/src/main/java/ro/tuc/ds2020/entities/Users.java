package ro.tuc.ds2020.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // puse de la mine
    @Email(regexp="[A-Za-z0-9_.-]+@(.+)$")
    private String email;
    @NotBlank(message = "username cannot be empty")
    private String username;
    //@NotBlank(message = "password cannot be empty")
    @Size(min=4)
    private String password;
    @Min(value = 0, message = "active must be at least 0")
    @Max(value = 1, message = "active must be at least 1")
    private Integer active;

    public String toString(){
        return "user: id = " + this.id +", username = " + this.username + ", password = " + this.password;
    }

}
