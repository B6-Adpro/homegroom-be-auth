package hoomgroom.auth.dto;

import hoomgroom.auth.model.Role;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class JWTUserDTO {
    private UUID id;
    private String username;
    private String firstname;
    private String lastname;
    private Role role;
}
