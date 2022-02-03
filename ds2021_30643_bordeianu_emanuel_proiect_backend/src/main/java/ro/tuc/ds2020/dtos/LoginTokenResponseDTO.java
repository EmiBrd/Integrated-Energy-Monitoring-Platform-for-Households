package ro.tuc.ds2020.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginTokenResponseDTO {

    private String role;
    private Long id;
    private String token;

}
