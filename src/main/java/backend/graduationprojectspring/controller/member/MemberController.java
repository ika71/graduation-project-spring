package backend.graduationprojectspring.controller.member;

import backend.graduationprojectspring.controller.member.dto.MemberCreateDto;
import backend.graduationprojectspring.controller.member.dto.MemberLoginDto;
import backend.graduationprojectspring.entity.Member;
import backend.graduationprojectspring.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/signup")
    public ResponseEntity<?> memberCreate(@RequestBody @Validated MemberCreateDto memberCreateDto){
        Member createdMember = memberService.create(memberCreateDto.toMember());

        return ResponseEntity.ok().body(createdMember.getName());
    }
    @PostMapping("/signin")
    public ResponseEntity<?> memberLogin(@RequestBody @Validated MemberLoginDto memberLoginDto){
        String token = memberService.getToken(memberLoginDto.getEmail(), memberLoginDto.password);

        if(token == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 실패");
        }
        return ResponseEntity.ok().body(new LoginSuccessDto(token));
    }
    @Getter
    @RequiredArgsConstructor
    private static class LoginSuccessDto{
        private final String token;
    }
}
