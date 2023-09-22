package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.entity.Evaluation;
import backend.graduationprojectspring.entity.EvaluationItem;
import backend.graduationprojectspring.exception.DuplicateException;
import backend.graduationprojectspring.exception.NotExistsException;
import backend.graduationprojectspring.repository.ElectronicDeviceRepo;
import backend.graduationprojectspring.repository.EvaluationItemRepo;
import backend.graduationprojectspring.repository.EvaluationRepo;
import backend.graduationprojectspring.service.dto.DeviceDetailAndAvgDto;
import jakarta.persistence.EntityManager;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class ElectronicDeviceServiceTest {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ElectronicDeviceService deviceService;
    @Autowired
    ElectronicDeviceRepo deviceRepo;
    @Autowired
    EvaluationItemRepo evalItemRepo;
    @Autowired
    EvaluationRepo evalRepo;
    @Autowired
    EntityManager em;

    //테스트용 데이터
    Category laptop;
    Category smartPhone;
    ElectronicDevice galaxyBook;
    ElectronicDevice macBook;
    ElectronicDevice galaxyPhone;
    EvaluationItem galaxyBookPrice;

    @BeforeEach
    void beforeEach(){
        //카테고리 저장
        laptop = categoryService.create("노트북");
        smartPhone = categoryService.create("스마트폰");

        //전자제품 저장
        galaxyBook = new ElectronicDevice("갤럭시북", laptop);
        macBook = new ElectronicDevice("맥북", laptop);
        galaxyPhone = new ElectronicDevice("갤럭시", smartPhone);

        galaxyBook = deviceService.create("갤럭시북", laptop.getId());
        macBook = deviceService.create("맥북", laptop.getId());
        galaxyPhone = deviceService.create("갤럭시", smartPhone.getId());

        //평가항목 저장
        galaxyBookPrice = evalItemRepo.save(
                new EvaluationItem("가격", galaxyBook));

        //평점 저장
        Evaluation evaluation1 = new Evaluation(5, galaxyBookPrice);
        Evaluation evaluation2 = new Evaluation(1, galaxyBookPrice);
        evalRepo.saveAll(List.of(evaluation1, evaluation2));

        em.flush();
        em.clear();
    }

    @Test
    void create() {
        assertThat(macBook.getId()).isNotNull();
        assertThat(macBook.getName()).isNotNull();
        assertThat(macBook.getCreatedBy()).isNotNull();
        assertThat(macBook.getModifiedBy()).isNotNull();
        assertThat(macBook.getCreatedTime()).isNotNull();
        assertThat(macBook.getUpdatedTime()).isNotNull();

        //중복된 전자제품이 있으면 예외가 발생해야 한다.
        assertThatThrownBy(()-> deviceService.create("맥북", smartPhone.getId()))
                .isInstanceOf(DuplicateException.class);
    }

    @Test
    void pagingJoinCategory() {
        List<ElectronicDevice> paging1 = deviceService.pagingJoinCategory(1, 2);
        List<ElectronicDevice> paging2 = deviceService.pagingJoinCategory(2, 2);

        assertThat(paging1.size()).isEqualTo(2);
        assertThat(paging2.size()).isEqualTo(1);

        //이름으로 정렬된 순서가 맞아야 한다.
        assertThat(paging1.get(0).getName()).isEqualTo("갤럭시");
        assertThat(paging1.get(1).getName()).isEqualTo("갤럭시북");
        assertThat(paging2.get(0).getName()).isEqualTo("맥북");

        //카테고리는 join하기 때문에 Eager 로딩이 되어야 함
        assertThat(Hibernate.isInitialized(paging1.get(0).getCategory()))
                .isTrue();

        //평가항목은 join하지 않았기 때문에 Lazy 로딩이 되어야 함
        assertThat(Hibernate.isInitialized(paging1.get(0)
                .getEvaluationItemList())).isFalse();
    }

    @Test
    void pagingJoinCategoryAndEvalItem() {
        List<ElectronicDevice> paging1 = deviceService
                .pagingJoinCategoryAndEvalItem(
                        1,
                        2,
                        null,
                        null);
        List<ElectronicDevice> paging2 = deviceService
                .pagingJoinCategoryAndEvalItem(
                        2,
                        2,
                        null,
                        null);

        assertThat(paging1.size()).isEqualTo(2);
        assertThat(paging2.size()).isEqualTo(1);

        //id 내림차순으로 정렬된 순서가 맞아야 한다.
        assertThat(paging1.get(0).getName()).isEqualTo("갤럭시");
        assertThat(paging1.get(1).getName()).isEqualTo("맥북");
        assertThat(paging2.get(0).getName()).isEqualTo("갤럭시북");

        //카테고리는 join하기 때문에 Eager 로딩이 되어야 한다.
        assertThat(Hibernate.isInitialized(paging1.get(0).getCategory()))
                .isTrue();
        //평가항목과는 leftjoin하기 때문에 Eager 로딩이 되어야 한다.
        assertThat(Hibernate.isInitialized(paging1.get(0).getEvaluationItemList()))
                .isTrue();

        //검색 조건에 걸리는 전자제품은 맥북 1개다.
        List<ElectronicDevice> conditionPaging1 = deviceService
                .pagingJoinCategoryAndEvalItem(
                        1,
                        10,
                        "맥",
                        null);
        assertThat(conditionPaging1.size()).isEqualTo(1);

        //검색 조건에 걸리는 전자제품은 노트북 카테고리에 속한 2개다.
        List<ElectronicDevice> conditionPaging2 = deviceService
                .pagingJoinCategoryAndEvalItem(
                        1,
                        10,
                        null,
                        "노트북");
        assertThat(conditionPaging2.size()).isEqualTo(2);

        //검색 조건에 걸리는 전자제품은 1개(갤럭시북)다
        List<ElectronicDevice> conditionPaging3 = deviceService
                .pagingJoinCategoryAndEvalItem(
                        1,
                        10,
                        "갤럭시",
                        "노트북");
        assertThat(conditionPaging3.size()).isEqualTo(1);
    }

    @Test
    void totalCount() {
        assertThat(deviceService.totalCount())
                .isEqualTo(3);
    }

    @Test
    void update() {
        deviceService.update(macBook.getId(), "맥북프로", laptop.getId());
        em.flush();
        em.clear();

        ElectronicDevice findMacBook = deviceRepo.findById(macBook.getId())
                .orElseThrow();
        assertThat(findMacBook.getName()).isEqualTo("맥북프로");

        //이름 수정 시 이미 존재하는 전자제품이 있으면 예외가 발생해야 한다.
        assertThatThrownBy(()->
                deviceService.update(galaxyBook.getId(), "갤럭시", laptop.getId()))
                .isInstanceOf(DuplicateException.class);

        //이름 수정 시 해당하는 전자제품이 없으면 예외가 발생해야 한다.
        assertThatThrownBy(()->
                deviceService.update(999L, "픽셀", smartPhone.getId()))
                .isInstanceOf(NotExistsException.class);
    }

    @Test
    void delete() {
        deviceService.delete(macBook.getId());

        em.flush();
        em.clear();

        Optional<ElectronicDevice> expectedNull = deviceRepo.findById(macBook.getId());
        assertThat(expectedNull.isEmpty()).isTrue();
    }

//    @Test
//    void setImage() {
//    }

    @Test
    void findOneDetail() {
        DeviceDetailAndAvgDto findDetail = deviceService.findOneDetail(galaxyBook.getId());
        ElectronicDevice findDevice = findDetail.getDevice();
        Map<Long, Double> avg = findDetail.getAvgGroupByEvalItemMap();

        assertThat(Hibernate.isInitialized(findDevice.getCategory()))
                .isTrue();
        assertThat(Hibernate.isInitialized(findDevice.getEvaluationItemList()))
                .isTrue();

        assertThat(avg.get(galaxyBookPrice.getId()))
                .isEqualTo(3);
    }

    @Test
    void countByCondition() {
        assertThat(deviceService.countByCondition(null, null))
                .isEqualTo(3);

        assertThat(deviceService.countByCondition("갤럭시", null))
                .isEqualTo(2);

        assertThat(deviceService.countByCondition(null, "스마트폰"))
                .isEqualTo(1);

        assertThat(deviceService.countByCondition("갤럭시", "노트북"))
                .isEqualTo(1);
    }
}