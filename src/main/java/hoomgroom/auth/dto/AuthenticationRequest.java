package hoomgroom.auth.dto;

import lombok.*;

@Getter
@Builder
@Generated
public class AuthenticationRequest {
    private String username;
    String password;
}
