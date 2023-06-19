package backend.graduationprojectspring.controller.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class MemberLoginDto {
    @Email
    public String email;
    @NotEmpty
    public String password;
}
