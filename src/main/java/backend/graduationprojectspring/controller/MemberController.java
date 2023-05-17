package backend.graduationprojectspring.controller;

import backend.graduationprojectspring.controller.dto.CreateMemberDto;
import backend.graduationprojectspring.entity.Member;
import backend.graduationprojectspring.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/signup")
    public ResponseEntity<?> createMember(@RequestBody @Validated CreateMemberDto createMemberDto){
        Member createdMember = memberService.create(createMemberDto.toMember());

        return ResponseEntity.ok().body(createdMember.getName());
    }
}
