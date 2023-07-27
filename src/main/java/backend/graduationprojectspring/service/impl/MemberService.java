package backend.graduationprojectspring.service.impl;

import backend.graduationprojectspring.config.security.TokenProvider;
import backend.graduationprojectspring.entity.Member;
import backend.graduationprojectspring.exception.DuplicateException;
import backend.graduationprojectspring.repository.MemberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepo memberRepo;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    /**
     * 회원가입
     * @param member email이나 name이 데이터 베이스에 중복이 없어야 함
     * @return 저장된 member
     * @throws DuplicateException email이나 name이 데이터 베이스에 중복
     */
    public Member create(Member member){
        if(memberRepo.existsByEmailOrName(member.getEmail(), member.getName())){
            throw new DuplicateException("이미 존재하는 이메일 또는 이름으로 회원가입을 시도하고 있습니다.");
        }
        member.passwordEncode(passwordEncoder);

        return memberRepo.save(member);
    }

    /**
     * email을 가지고 있는 아이디가 존재하고 비밀번호가 일치하면 토큰을 반환
     * @param email
     * @param password
     * @return JWT or null
     */
    @Transactional(readOnly = true)
    public String getToken(String email, String password){
        Member findMember = memberRepo.findByEmail(email);

        if(findMember != null && passwordEncoder.matches(password, findMember.getPassword())){
            return tokenProvider.createToken(findMember);
        }
        return null;
    }
}
