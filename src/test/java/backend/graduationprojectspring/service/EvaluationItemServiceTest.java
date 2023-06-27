package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.entity.EvaluationItem;
import backend.graduationprojectspring.repository.EvaluationItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Test
    void findAllByElectronicDeviceId() {
        Category category = new Category("스마트폰");
        Category savedCategory = categoryService.create(category);

        ElectronicDevice device1 = new ElectronicDevice("갤럭시", savedCategory);
        ElectronicDevice savedDevice1 = deviceService.create(device1);
        ElectronicDevice device2 = new ElectronicDevice("아이폰", savedCategory);
        ElectronicDevice savedDevice2 = deviceService.create(device2);

        EvaluationItem item = new EvaluationItem("성능", savedDevice1);
        itemService.create(item);

        List<EvaluationItem> findItemList1 =
                itemService.findAllByElectronicDeviceId(savedDevice1.getId());
        assertThat(findItemList1.size()).isEqualTo(1);

        List<EvaluationItem> findItemList2 =
                itemService.findAllByElectronicDeviceId(savedDevice2.getId());
        assertThat(findItemList2.size()).isEqualTo(0);
    }
}