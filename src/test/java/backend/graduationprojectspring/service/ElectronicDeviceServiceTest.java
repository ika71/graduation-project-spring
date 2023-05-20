package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.repository.ElectronicDeviceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ElectronicDeviceServiceTest {
    @Autowired
    ElectronicDeviceService electronicDeviceService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ElectronicDeviceRepository electronicDeviceRepository;
    @Test
    void create() {
        Category category = new Category("스마트폰");
        Category savedCategory = categoryService.create(category);

        ElectronicDevice electronicDevice = new ElectronicDevice("갤럭시");
        ElectronicDevice savedElectronicDevice = electronicDeviceService.create(electronicDevice, savedCategory.getId());

        ElectronicDevice findElectronicDevice = electronicDeviceRepository.findById(savedElectronicDevice.getId()).orElseThrow();
        assertThat(findElectronicDevice.getId()).isEqualTo(savedElectronicDevice.getId());
        assertThat(findElectronicDevice.getName()).isEqualTo(savedElectronicDevice.getName());
        assertThat(findElectronicDevice.getCategory().getId()).isEqualTo(savedElectronicDevice.getCategory().getId());
    }
}