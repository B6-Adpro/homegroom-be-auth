package hoomgroom.auth.dto;

import lombok.Builder;
import lombok.Generated;
import lombok.Getter;

@Getter
@Builder
@Generated
public class AuthenticationResponse {
    private String message;
    private String token;
}
