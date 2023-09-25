package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.entity.EvaluationItem;
import backend.graduationprojectspring.entity.Member;
import backend.graduationprojectspring.repository.EvaluationRepo;
import backend.graduationprojectspring.service.dto.EvalItemAndEvaluationDto;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class EvaluationServiceTest {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ElectronicDeviceService deviceService;
    @Autowired
    EvaluationItemService evalItemService;
    @Autowired
    EvaluationService evalService;
    @Autowired
    MemberService memberService;
    @Autowired
    EvaluationRepo evalRepo;
    @Autowired
    EntityManager em;

    ElectronicDevice galaxy;
    EvaluationItem price;
    EvaluationItem display;
    Member member;

    @BeforeEach
    void beforeEach(){
        Category smartPhone = categoryService.create("스마트폰");
        galaxy = deviceService.create("갤럭시", smartPhone.getId());
        price = evalItemService.create("가격", galaxy.getId());
        display = evalItemService.create("디스플레이", galaxy.getId());

        member = memberService.create(Member.of("email@email.com", "nickName", "1234"));
    }

    @Test
    void put() {
        Map<Long, Integer> map = new HashMap<>();
        map.put(price.getId(), 5);
        map.put(display.getId(), 3);

        evalService.put(member.getId().toString(), map);
        em.flush();
        em.clear();

        assertThat(evalRepo.findAll().size()).isEqualTo(2);
    }

    @Test
    void findAllByMemberIdAndDeviceId() {
        List<EvalItemAndEvaluationDto> findEval = evalService.findAllByMemberIdAndDeviceId(member.getId().toString(), galaxy.getId());
        assertThat(findEval.size()).isEqualTo(2);
    }
}