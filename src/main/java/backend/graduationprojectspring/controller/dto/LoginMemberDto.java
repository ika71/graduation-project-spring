package backend.graduationprojectspring.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class LoginMemberDto {
    @Email
    public String email;
    @NotEmpty
    public String password;
}
