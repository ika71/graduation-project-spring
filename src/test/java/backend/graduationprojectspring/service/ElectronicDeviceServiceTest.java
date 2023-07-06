package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.entity.EvaluationItem;
import backend.graduationprojectspring.repository.ElectronicDeviceRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ElectronicDeviceServiceTest {
    @Autowired
    ElectronicDeviceService deviceService;
    @Autowired
    EvaluationItemService evaluationItemService;
    @Autowired
    ElectronicDeviceRepository deviceRepository;
    @Autowired
    CategoryService categoryService;
    @Autowired
    EntityManager em;

    //저장된 데이터
    Category category;
    ElectronicDevice device1;
    ElectronicDevice device2;
    ElectronicDevice device3;
    @BeforeEach
    void beforeEach(){
        category = categoryService.create(new Category("스마트폰"));

        device1 = deviceService.create(new ElectronicDevice("아이폰", category));
        device2 = deviceService.create(new ElectronicDevice("갤럭시", category));
        device3 = deviceService.create(new ElectronicDevice("픽셀", category));
    }
    @Test
    void create() {
        ElectronicDevice device = new ElectronicDevice("모토롤라", category);
        ElectronicDevice savedDevice = deviceService.create(device);
        ElectronicDevice findDevice = deviceRepository.findById(savedDevice.getId()).orElseThrow();

        assertThat(findDevice.getId()).isEqualTo(savedDevice.getId());
        assertThat(findDevice.getName()).isEqualTo(savedDevice.getName());
        assertThat(findDevice.getCategory().getId()).isEqualTo(savedDevice.getCategory().getId());

    }

    @Test
    void paging() {
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
        assertThat(deviceService.totalCount()).isEqualTo(3);
    }

    @Test
    void update() {
        Category changeCategory = categoryService.create(new Category("노트북"));
        ElectronicDevice changeDevice = new ElectronicDevice("갤럭시북", changeCategory);

        deviceService.update(device1.getId(), changeDevice);
        ElectronicDevice findDevice = deviceRepository.findById(device1.getId()).orElseThrow();

        assertThat(findDevice.getName()).isEqualTo(changeDevice.getName());
        assertThat(findDevice.getCategory().getId()).isEqualTo(changeDevice.getCategory().getId());

    }

    @Test
    void delete() {
        deviceService.delete(device1.getId());

        assertThat(deviceService.totalCount()).isEqualTo(2);
    }
    @Test
    void getReferenceById(){
        ElectronicDevice proxyDevice = deviceService.getReferenceById(device1.getId());
        boolean proxy = em.getEntityManagerFactory()
                .getPersistenceUnitUtil().isLoaded(proxyDevice);

        assertThat(proxy).isTrue();
    }

    @Test
    void fetchJoinEvaluationItem(){
        evaluationItemService.create(new EvaluationItem("가격", device1));
        ElectronicDevice findDevice = deviceRepository.findById(device1.getId()).orElseThrow();
        em.flush();
        em.clear();

        assertThat(findDevice.getEvaluationItemList().size()).isEqualTo(0);

        List<ElectronicDevice> deviceList = new ArrayList<>();
        deviceList.add(findDevice);

        List<ElectronicDevice> fetchJoinDeviceList = deviceService.fetchJoinEvaluationItem(deviceList);
        ElectronicDevice fetchJoinDevice1 = fetchJoinDeviceList.get(0);

        assertThat(fetchJoinDevice1.getEvaluationItemList().size()).isEqualTo(1);
    }
}