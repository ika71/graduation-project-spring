package backend.graduationprojectspring.controller.dto;

import backend.graduationprojectspring.constant.Role;
import backend.graduationprojectspring.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class CreateMemberDto {
    @Email
    private String email;
    @NotEmpty
    private String name;
    @NotEmpty
    private String password;

    public Member toMember(){
        return new Member(email, name, password, Role.USER);
    }
}
