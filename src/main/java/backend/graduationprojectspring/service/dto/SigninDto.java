package backend.graduationprojectspring.service.dto;

import lombok.Getter;
import lombok.ToString;

/**
 * 로그인 시 발급되는 리프레쉬 토큰과 액세스 토큰을 표현하는 dto
 */
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
