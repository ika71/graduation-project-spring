package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.repository.ElectronicDeviceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ElectronicDeviceServiceTest {
    @Autowired
    ElectronicDeviceService deviceService;
    @Autowired
    ElectronicDeviceRepository deviceRepository;
    @Autowired
    CategoryService categoryService;

    @Test
    void create() {
        Category category = new Category("스마트폰");
        Category savedCategory = categoryService.create(category);

        ElectronicDevice device = new ElectronicDevice("갤럭시", savedCategory);
        ElectronicDevice savedDevice = deviceService.create(device);
        ElectronicDevice findDevice = deviceRepository.findById(savedDevice.getId()).orElseThrow();

        assertThat(findDevice.getId()).isEqualTo(savedDevice.getId());
        assertThat(findDevice.getName()).isEqualTo(savedDevice.getName());
        assertThat(findDevice.getCategory().getId()).isEqualTo(savedDevice.getCategory().getId());

    }

    @Test
    void paging() {
        Category category = new Category("스마트폰");
        Category savedCategory = categoryService.create(category);

        ElectronicDevice device1 = new ElectronicDevice("아이폰", savedCategory);
        ElectronicDevice device2 = new ElectronicDevice("갤럭시", savedCategory);
        ElectronicDevice device3 = new ElectronicDevice("픽셀", savedCategory);
        deviceService.create(device1);
        deviceService.create(device2);
        deviceService.create(device3);

        List<ElectronicDevice> deviceList1 = deviceService.paging(1, 2);
        assertThat(deviceList1.get(0).getName()).isEqualTo(device2.getName());
        assertThat(deviceList1.get(0).getCategory().getName()).isEqualTo(device2.getCategory().getName());
        assertThat(deviceList1.get(1).getName()).isEqualTo(device1.getName());
        assertThat(deviceList1.get(1).getCategory().getName()).isEqualTo(device1.getCategory().getName());

        List<ElectronicDevice> deviceList2 = deviceService.paging(2, 2);
        assertThat(deviceList2.get(0).getName()).isEqualTo(device3.getName());
        assertThat(deviceList2.get(0).getCategory().getName()).isEqualTo(device3.getCategory().getName());
    }

    @Test
    void totalCount() {
        assertThat(deviceService.totalCount()).isEqualTo(0);

        Category category = new Category("스마트폰");
        Category savedCategory = categoryService.create(category);

        ElectronicDevice device1 = new ElectronicDevice("아이폰", savedCategory);
        ElectronicDevice device2 = new ElectronicDevice("갤럭시", savedCategory);
        ElectronicDevice device3 = new ElectronicDevice("픽셀", savedCategory);
        deviceService.create(device1);
        deviceService.create(device2);
        deviceService.create(device3);

        assertThat(deviceService.totalCount()).isEqualTo(3);
    }

    @Test
    void update() {
        Category category = new Category("스마트폰");
        Category savedCategory = categoryService.create(category);

        ElectronicDevice device = new ElectronicDevice("아이폰", savedCategory);
        ElectronicDevice savedDevice = deviceService.create(device);

        Category changeCategory = new Category("노트북");
        categoryService.create(changeCategory);
        ElectronicDevice changeDevice = new ElectronicDevice("갤럭시북", changeCategory);

        deviceService.update(savedDevice.getId(), changeDevice);
        ElectronicDevice findDevice = deviceRepository.findById(savedDevice.getId()).orElseThrow();

        assertThat(findDevice.getName()).isEqualTo(changeDevice.getName());
        assertThat(findDevice.getCategory().getId()).isEqualTo(changeDevice.getCategory().getId());

    }

    @Test
    void delete() {
        Category category = new Category("스마트폰");
        Category savedCategory = categoryService.create(category);

        ElectronicDevice device = new ElectronicDevice("아이폰", savedCategory);
        ElectronicDevice savedDevice = deviceService.create(device);
        assertThat(deviceService.totalCount()).isEqualTo(1);

        deviceService.delete(savedDevice.getId());

        assertThat(deviceService.totalCount()).isEqualTo(0);
    }
}