package backend.graduationprojectspring.service.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SigninDto {
    private final String refreshToken;
    private final String accessToken;

    public SigninDto(String refreshToken, String accessToken) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }
}
