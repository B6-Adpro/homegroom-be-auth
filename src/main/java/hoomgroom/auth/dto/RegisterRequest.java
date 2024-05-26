package hoomgroom.auth.dto;

import lombok.Builder;
import lombok.Generated;
import lombok.Getter;

@Getter
@Builder
@Generated
public class RegisterRequest {
    private String username;
    private String firstname;
    private String lastname;
    private String password;
}
