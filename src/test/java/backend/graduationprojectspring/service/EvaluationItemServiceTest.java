package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.entity.EvaluationItem;
import backend.graduationprojectspring.repository.EvaluationItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class EvaluationItemServiceTest {
    @Autowired
    EvaluationItemService itemService;
    @Autowired
    ElectronicDeviceService deviceService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    EvaluationItemRepository itemRepository;

    //저장된 데이터
    Category category;
    ElectronicDevice device1;
    ElectronicDevice device2;
    EvaluationItem evaluationItem;

    @BeforeEach
    void beforeEach(){
        category = categoryService.create(new Category("스마트폰"));
        device1 = deviceService.create(new ElectronicDevice("갤럭시"), category.getId());
        device2 = deviceService.create(new ElectronicDevice("아이폰"), category.getId());
        evaluationItem = itemService.create(new EvaluationItem("성능", device1));
    }
    @Test
    void create() {
        EvaluationItem findItem = itemRepository.findById(evaluationItem.getId()).orElseThrow();

        assertThat(evaluationItem.getId()).isEqualTo(findItem.getId());
        assertThat(evaluationItem.getName()).isEqualTo(findItem.getName());
        assertThat(evaluationItem.getElectronicDevice().getId())
                .isEqualTo(findItem.getElectronicDevice().getId());
    }

    @Test
    void findAllByElectronicDeviceId() {
        List<EvaluationItem> findItemList1 =
                itemService.findAllByElectronicDeviceId(device1.getId());
        assertThat(findItemList1.size()).isEqualTo(1);

        List<EvaluationItem> findItemList2 =
                itemService.findAllByElectronicDeviceId(device2.getId());
        assertThat(findItemList2.size()).isEqualTo(0);
    }

    @Test
    void updateName() {
        itemService.updateName(evaluationItem.getId(), "가격");
        EvaluationItem findItem = itemRepository.findById(evaluationItem.getId()).orElseThrow();

        assertThat(findItem.getName()).isEqualTo("가격");
    }

    @Test
    void delete() {
        assertThat(itemRepository.count()).isEqualTo(1);
        itemService.delete(evaluationItem.getId());
        assertThat(itemRepository.count()).isEqualTo(0);

        Optional<EvaluationItem> findItemOptional = itemRepository.findById(evaluationItem.getId());

        assertThatThrownBy(findItemOptional::orElseThrow)
                .isInstanceOf(NoSuchElementException.class);
    }
}