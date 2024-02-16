package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.exception.HttpError;
import backend.graduationprojectspring.repository.CategoryRepo;
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
class CategoryServiceTest {
    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    EntityManager em;

    //테스트용 데이터
    Category laptop;
    Category monitor;
    Category smartPhone;

    @BeforeEach
    void beforeEach(){
        //카테고리 저장
        laptop = categoryService.create("노트북");
        monitor = categoryService.create("모니터");
        smartPhone = categoryService.create("스마트폰");

        em.flush();
        em.clear();
    }

    @Test
    void create() {
        assertThat(laptop.getId()).isNotNull();
        assertThat(laptop.getName()).isNotNull();
        assertThat(laptop.getCreatedBy()).isNotNull();
        assertThat(laptop.getModifiedBy()).isNotNull();
        assertThat(laptop.getCreatedTime()).isNotNull();
        assertThat(laptop.getUpdatedTime()).isNotNull();

        //중복된 이름의 카테고리가 있을 시 예외가 발생해야 한다.
        assertThatThrownBy(()-> categoryService.create("노트북"))
                .isInstanceOf(HttpError.class);
    }

    @Test
    void paging() {
        List<Category> paging1 = categoryService.paging(1, 2);
        List<Category> paging2 = categoryService.paging(2, 2);
        assertThat(paging1.size()).isEqualTo(2);
        assertThat(paging2.size()).isEqualTo(1);

        //이름으로 정렬된 순서가 맞아야 한다.
        assertThat(paging1.get(0).getName()).isEqualTo(laptop.getName());
        assertThat(paging1.get(1).getName()).isEqualTo(monitor.getName());
        assertThat(paging2.get(0).getName()).isEqualTo(smartPhone.getName());
    }

    @Test
    void totalCount() {
        long totalCount = categoryService.totalCount();
        assertThat(totalCount).isEqualTo(3);
    }

    @Test
    void updateName() {
        categoryService.updateName(laptop.getId(), "울트라북");
        em.flush();
        em.clear();

        Category updatedLaptop = categoryRepo.findById(laptop.getId())
                .orElseThrow();
        assertThat(updatedLaptop.getName()).isEqualTo("울트라북");

        //중복된 이름이 이미 존재 시 예외가 발생해야 한다.
        assertThatThrownBy(()-> categoryService.updateName(laptop.getId(), "스마트폰"))
                .isInstanceOf(HttpError.class);

        //해당하는 카테고리가 존재하지 않으면 예외가 발생해야 한다.
        assertThatThrownBy(()-> categoryService.updateName(999L, "nothing"))
                .isInstanceOf(HttpError.class);
    }

    @Test
    void delete() {
        categoryService.delete(laptop.getId());
        em.flush();
        em.clear();

        Optional<Category> expectedNull = categoryRepo.findById(laptop.getId());
        assertThat(expectedNull.isEmpty()).isTrue();
    }

    @Test
    void findAll() {
        List<Category> allCategory = categoryService.findAll();

        assertThat(allCategory.size()).isEqualTo(3);
    }
}