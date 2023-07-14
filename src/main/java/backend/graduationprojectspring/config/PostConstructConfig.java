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
        Member admin = Member.ofCreateAdmin(
                "admin@admin.com",
                "admin",
                "admin@");
        memberService.create(admin);

        //카테고리 데이터 추가
        Category savedCategory1 = categoryService.create("스마트폰");
        Category savedCategory2 = categoryService.create("노트북");

        //전자제품 데이터 추가
        ElectronicDevice device1 = deviceService.create("갤럭시", savedCategory1.getId());
        deviceService.create("아이폰", savedCategory1.getId());
        deviceService.create("픽셀", savedCategory1.getId());

        deviceService.create("갤럭시북", savedCategory2.getId());
        deviceService.create("맥북", savedCategory2.getId());
        deviceService.create("그램", savedCategory2.getId());

        //평가 항목 추가
        EvaluationItem evaluationItem1 = new EvaluationItem("가격");
        EvaluationItem evaluationItem2 = new EvaluationItem("디스플레이");
        EvaluationItem evaluationItem3 = new EvaluationItem("성능");

        itemService.create(evaluationItem1, device1.getId());
        itemService.create(evaluationItem2, device1.getId());
        itemService.create(evaluationItem3, device1.getId());
    }
}
