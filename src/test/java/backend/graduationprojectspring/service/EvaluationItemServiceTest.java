package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.entity.EvaluationItem;
import backend.graduationprojectspring.exception.DuplicateException;
import backend.graduationprojectspring.exception.NotExistsException;
import backend.graduationprojectspring.repository.EvaluationItemRepo;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class EvaluationItemServiceTest {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ElectronicDeviceService deviceService;
    @Autowired
    EvaluationItemService evalItemService;
    @Autowired
    EvaluationItemRepo evalItemRepo;
    @Autowired
    EntityManager em;
    ElectronicDevice galaxy;

    @BeforeEach
    void beforeEach(){
        Category smartPhone = categoryService.create("스마트폰");
        galaxy = deviceService.create("갤럭시", smartPhone.getId());
    }

    @Test
    void create() {
        EvaluationItem evalItem = evalItemService.create("성능", galaxy.getId());

        assertThat(evalItem.getId()).isNotNull();
        assertThatThrownBy(()-> evalItemService.create("성능", galaxy.getId()))
                .isInstanceOf(DuplicateException.class);
    }

    @Test
    void findAllByElectronicDeviceId() {
        EvaluationItem evalItem1 = evalItemService.create("성능", galaxy.getId());
        EvaluationItem evalItem2 = evalItemService.create("가성비", galaxy.getId());

        List<EvaluationItem> deviceEvalItemList = evalItemService.findAllByElectronicDeviceId(galaxy.getId());
        assertThat(deviceEvalItemList.size()).isEqualTo(2);
    }

    @Test
    void updateName() {
        EvaluationItem evalItem = evalItemService.create("성능", galaxy.getId());
        em.flush();
        em.clear();

        evalItemService.updateName(evalItem.getId(), "가격");
        EvaluationItem updatedEvalItem = evalItemRepo.findById(evalItem.getId())
                .orElseThrow();
        assertThat(updatedEvalItem.getName()).isEqualTo("가격");

        evalItemService.create("디스플레이", galaxy.getId());

        assertThatThrownBy(()-> evalItemService.updateName(evalItem.getId(), "디스플레이"))
                .isInstanceOf(DuplicateException.class);
        assertThatThrownBy(()-> evalItemService.updateName(999L, "내구성"))
                .isInstanceOf(NotExistsException.class);
    }

    @Test
    void delete() {
        EvaluationItem evalItem = evalItemService.create("성능", galaxy.getId());
        evalItemService.delete(evalItem.getId());

        em.flush();
        em.clear();

        Optional<EvaluationItem> deletedEvalItem = evalItemRepo.findById(evalItem.getId());
        assertThat(deletedEvalItem.isEmpty()).isTrue();
    }
}