package backend.graduationprojectspring.controller;

import backend.graduationprojectspring.constant.Role;
import backend.graduationprojectspring.entity.Member;
import backend.graduationprojectspring.service.MemberService;
import backend.graduationprojectspring.service.dto.SigninDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @GetMapping
    public MemberInfoDto memberInfo(){
        String memberId = SecurityContextHolder.getContext().getAuthentication().getName();

        Member member = memberService.findById(Long.valueOf(memberId));

        return new MemberInfoDto(member);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> memberCreate(@RequestBody @Validated MemberCreateDto memberCreateDto){
        Member createdMember = memberService.create(memberCreateDto.toMember());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdMember.getName());
    }
    @PostMapping("/signin")
    public ResponseEntity<?> memberLogin(@RequestBody @Validated MemberLoginDto memberLoginDto){
        Optional<SigninDto> signinDto = memberService.signin(memberLoginDto.getEmail(), memberLoginDto.password);

        if(signinDto.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 실패");
        }
        return ResponseEntity.ok().body(new LoginSuccessDto(signinDto.get()));
    }
    @Getter
    @ToString
    public static class MemberInfoDto{
        String userName;
        Role role;

        public MemberInfoDto(Member member) {
            this.userName = member.getName();
            this.role = member.getRole();
        }
    }
    @Getter
    @ToString
    public static class LoginSuccessDto{
        private final String refreshToken;
        private final String accessToken;

        public LoginSuccessDto(SigninDto signinDto) {
            this.refreshToken = signinDto.getRefreshToken();
            this.accessToken = signinDto.getAccessToken();
        }
    }

    @Getter
    @ToString
    public static class MemberCreateDto {
        @Email
        private String email;
        @NotEmpty
        private String name;
        @NotEmpty
        private String password;

        public Member toMember(){
            return Member.of(email, name, password);
        }
    }

    @Getter
    @ToString
    public static class MemberLoginDto {
        @Email
        public String email;
        @NotEmpty
        public String password;
    }
}
