package ro.tuc.ds2020.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Admins extends Users{

    private String phoneNumber;

    @Builder
    public Admins(Long id, String email, String username, String password, String phoneNumber, Integer active){
        super(id, email, username, password, active);
        this.phoneNumber = phoneNumber;
    }

}

