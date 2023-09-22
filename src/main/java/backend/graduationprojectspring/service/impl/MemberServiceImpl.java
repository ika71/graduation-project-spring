package backend.graduationprojectspring.service.impl;

import backend.graduationprojectspring.entity.Member;
import backend.graduationprojectspring.exception.DuplicateException;
import backend.graduationprojectspring.exception.NotExistsException;
import backend.graduationprojectspring.repository.MemberRepo;
import backend.graduationprojectspring.security.TokenProvider;
import backend.graduationprojectspring.service.MemberService;
import backend.graduationprojectspring.service.dto.SigninDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepo memberRepo;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Override
    public Member create(Member member){
        if(memberRepo.existsByEmail(member.getEmail())){
            throw new DuplicateException("이미 존재하는 이메일로 회원가입을 시도하고 있습니다.");
        }
        if(memberRepo.existsByName(member.getName())){
            throw new DuplicateException("이미 존재하는 닉네임으로 회원가입을 시도하고 있습니다.");
        }

        member.passwordEncode(passwordEncoder);

        return memberRepo.save(member);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SigninDto> signin(String email, String password){
        Optional<Member> findMember = memberRepo.findByEmail(email);

        if(findMember.isPresent() && passwordEncoder.matches(password, findMember.get().getPassword())){
            String refreshToken = tokenProvider.createRefreshToken(
                    findMember.get().getId().toString(),
                    findMember.get().getRole().toString());
            String accessToken = tokenProvider.createAccessToken(
                    findMember.get().getId().toString(),
                    findMember.get().getRole().toString());
            return Optional.of(new SigninDto(refreshToken, accessToken));
        }
        return Optional.empty();
    }

    @Override
    public Member findById(Long id) {
        return memberRepo.findById(id)
                .orElseThrow(() -> new NotExistsException("해당 하는 유저가 없습니다."));
    }

    @Override
    public String createAccessToken(String id, String role) {
        return tokenProvider.createAccessToken(id, role);
    }
}
