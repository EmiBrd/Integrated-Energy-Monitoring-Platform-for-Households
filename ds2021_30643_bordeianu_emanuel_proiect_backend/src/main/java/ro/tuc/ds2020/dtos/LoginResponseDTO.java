package ro.tuc.ds2020.dtos;

import lombok.*;

@Getter
@Setter
@Builder
public class LoginResponseDTO {

    private String role;
    private Long id;

}
