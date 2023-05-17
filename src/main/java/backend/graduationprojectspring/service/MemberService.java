package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Member;
import backend.graduationprojectspring.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member create(Member member){
        if(memberRepository.existsByEmailOrName(member.getEmail(), member.getName())){
            throw new IllegalArgumentException("이미 존재하는 이메일 또는 이름으로 회원가입을 시도하고 있습니다.");
        }

        return memberRepository.save(member);
    }

}
