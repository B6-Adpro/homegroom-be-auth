package hoomgroom.auth.dto;

import lombok.Builder;
import lombok.Generated;
import lombok.Getter;

@Getter
@Builder
@Generated
public class AuthenticationRequest {
    private String username;
    String password;
}
