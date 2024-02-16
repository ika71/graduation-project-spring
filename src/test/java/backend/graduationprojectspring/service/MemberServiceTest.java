package backend.graduationprojectspring.service;

import backend.graduationprojectspring.constant.Role;
import backend.graduationprojectspring.entity.Member;
import backend.graduationprojectspring.exception.HttpError;
import backend.graduationprojectspring.security.TokenProvider;
import backend.graduationprojectspring.service.dto.SigninDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    TokenProvider tokenProvider;

    Member member;
    @BeforeEach
    void beforeEach(){
        member = Member.of("email@email.com", "member", "1234");
        member = memberService.create(member);
    }
    @Test
    void create() {
        assertThat(member.getId()).isNotNull();
    }

    @Test
    void signin() {
        Optional<SigninDto> expectedNotNull = memberService.signin("email@email.com", "1234");
        assertThat(expectedNotNull.isPresent()).isTrue();

        Optional<SigninDto> expectedNull = memberService.signin("email@email.com", "12");
        assertThat(expectedNull.isEmpty()).isTrue();
    }

    @Test
    void findById() {
        Member member = memberService.findById(this.member.getId());
        assertThat(member.getId()).isNotNull();

        assertThatThrownBy(()-> memberService.findById(999L))
                .isInstanceOf(HttpError.class);

    }

    @Test
    void createAccessToken() {
        String token = memberService.createAccessToken(member.getId().toString(), Role.USER.toString());
        tokenProvider.validateAccessToken(token);
    }
}