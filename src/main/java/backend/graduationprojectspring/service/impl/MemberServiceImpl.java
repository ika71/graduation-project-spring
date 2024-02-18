package backend.graduationprojectspring.service.impl;

import backend.graduationprojectspring.entity.Member;
import backend.graduationprojectspring.exception.HttpError;
import backend.graduationprojectspring.repository.MemberRepo;
import backend.graduationprojectspring.security.TokenProvider;
import backend.graduationprojectspring.service.MemberService;
import backend.graduationprojectspring.service.dto.SigninDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
            throw new HttpError("이미 존재하는 이메일로 회원가입을 시도하고 있습니다.", HttpStatus.CONFLICT);
        }
        if(memberRepo.existsByName(member.getName())){
            throw new HttpError("이미 존재하는 닉네임으로 회원가입을 시도하고 있습니다.", HttpStatus.CONFLICT);
        }

        member.passwordEncode(passwordEncoder);

        return memberRepo.save(member);
    }

    @Override
    @Transactional(readOnly = true)
    public SigninDto signin(String email, String password){
        Optional<Member> findMember = memberRepo.findByEmail(email);

        if(findMember.isPresent() && passwordEncoder.matches(password, findMember.get().getPassword())){
            String refreshToken = tokenProvider.createRefreshToken(
                    findMember.get().getId().toString(),
                    findMember.get().getRole().toString());
            String accessToken = tokenProvider.createAccessToken(
                    findMember.get().getId().toString(),
                    findMember.get().getRole().toString());
            return new SigninDto(refreshToken, accessToken);
        }
        throw new HttpError("인증 실패", HttpStatus.UNAUTHORIZED);
    }

    @Override
    public Member findById(Long id) {
        return memberRepo.findById(id)
                .orElseThrow(() -> new HttpError("해당 하는 유저가 없습니다.", HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @Override
    public String createAccessToken(String id, String role) {
        return tokenProvider.createAccessToken(id, role);
    }
}
