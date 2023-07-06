package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Member;
import backend.graduationprojectspring.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    //저장된 데이터
    Member member;
    @BeforeEach
    void beforeEach(){
        member = memberService.create(new Member(
                "test@test.com",
                "test",
                "password"));
    }

    @Test
    void create() {
        Member findMember = memberRepository.findById(member.getId()).orElseThrow();

        assertThat(member.getId()).isEqualTo(findMember.getId());
        assertThat(member.getEmail()).isEqualTo(findMember.getEmail());
        assertThat(member.getName()).isEqualTo(findMember.getName());
        assertThat(member.getPassword()).isEqualTo(findMember.getPassword());

        Member member2 = new Member("test@test.com", "test", "password");
        Member member3 = new Member("member3@member.com", "test", "password");
        Member member4 = new Member("test@test.com", "member4", "password");

        assertThatThrownBy(()->memberService.create(member2)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(()->memberService.create(member3)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(()->memberService.create(member4)).isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    void getToken(){
        String token = memberService.getToken("test@test.com", "password");
        assertThat(token).isNotNull();

        String expectNull = memberService.getToken("noEmail", "password");
        assertThat(expectNull).isNull();

        expectNull = memberService.getToken("test@test.com", "1234");
        assertThat(expectNull).isNull();
    }
}