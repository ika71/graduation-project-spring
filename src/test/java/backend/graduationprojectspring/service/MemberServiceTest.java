package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Member;
import backend.graduationprojectspring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void create() {
        Member member1 = new Member("test@test.com", "test", "password");

        Member createdMember = memberService.create(member1);

        Member findMember = memberRepository.findById(createdMember.getId()).orElseThrow();

        assertThat(createdMember.getId()).isEqualTo(findMember.getId());
        assertThat(createdMember.getEmail()).isEqualTo(findMember.getEmail());
        assertThat(createdMember.getName()).isEqualTo(findMember.getName());
        assertThat(createdMember.getPassword()).isEqualTo(findMember.getPassword());

        Member member2 = new Member("test@test.com", "test", "password");
        Member member3 = new Member("member3@member.com", "test", "password");
        Member member4 = new Member("test@test.com", "member4", "password");

        assertThatThrownBy(()->memberService.create(member2)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(()->memberService.create(member3)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(()->memberService.create(member4)).isInstanceOf(IllegalArgumentException.class);
    }
}