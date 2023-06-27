package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.entity.EvaluationItem;
import backend.graduationprojectspring.repository.EvaluationItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    void create() {
        Category category = new Category("스마트폰");
        Category savedCategory = categoryService.create(category);

        ElectronicDevice device = new ElectronicDevice("갤럭시", savedCategory);
        ElectronicDevice savedDevice = deviceService.create(device);

        EvaluationItem item = new EvaluationItem("성능", savedDevice);
        EvaluationItem savedItem = itemService.create(item);
        EvaluationItem findItem = itemRepository.findById(savedItem.getId()).orElseThrow();

        assertThat(savedItem.getId()).isEqualTo(findItem.getId());
        assertThat(savedItem.getName()).isEqualTo(findItem.getName());
        assertThat(savedItem.getElectronicDevice().getId())
                .isEqualTo(findItem.getElectronicDevice().getId());
    }
}