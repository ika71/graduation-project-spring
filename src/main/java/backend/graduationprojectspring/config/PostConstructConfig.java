package backend.graduationprojectspring.config;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.entity.EvaluationItem;
import backend.graduationprojectspring.entity.Member;
import backend.graduationprojectspring.service.CategoryService;
import backend.graduationprojectspring.service.ElectronicDeviceService;
import backend.graduationprojectspring.service.EvaluationItemService;
import backend.graduationprojectspring.service.MemberService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class PostConstructConfig {
    private final MemberService memberService;
    private final CategoryService categoryService;
    private final ElectronicDeviceService deviceService;
    private final EvaluationItemService itemService;

    @PostConstruct
    public void post(){
        //테스트용 어드민 계정 생성
        Member admin = Member.createAdminOf(
                "admin@admin.com",
                "admin",
                "admin@");
        memberService.create(admin);

        //카테고리 데이터 추가
        Category category1 = new Category("스마트폰");
        Category category2 = new Category("노트북");
        Category savedCategory1 = categoryService.create(category1);
        Category savedCategory2 = categoryService.create(category2);

        //전자제품 데이터 추가
        ElectronicDevice device1 = new ElectronicDevice("갤럭시");
        ElectronicDevice device2 = new ElectronicDevice("아이폰");
        ElectronicDevice device3 = new ElectronicDevice("픽셀");
        ElectronicDevice savedDevice1 = deviceService.create(device1, savedCategory1.getId());
        deviceService.create(device2, savedCategory1.getId());
        deviceService.create(device3, savedCategory1.getId());

        ElectronicDevice device4 = new ElectronicDevice("갤럭시북");
        ElectronicDevice device5 = new ElectronicDevice("맥북");
        ElectronicDevice device6 = new ElectronicDevice("그램");
        deviceService.create(device4, savedCategory2.getId());
        deviceService.create(device5, savedCategory2.getId());
        deviceService.create(device6, savedCategory2.getId());

        //평가 항목 추가
        EvaluationItem evaluationItem1 = new EvaluationItem("가격");
        EvaluationItem evaluationItem2 = new EvaluationItem("디스플레이");
        EvaluationItem evaluationItem3 = new EvaluationItem("성능");

        itemService.create(evaluationItem1, device1.getId());
        itemService.create(evaluationItem2, device1.getId());
        itemService.create(evaluationItem3, device1.getId());
    }
}
