package backend.graduationprojectspring.config;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.service.CategoryService;
import backend.graduationprojectspring.service.ElectronicDeviceService;
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

    @PostConstruct
    public void post(){
        //테스트용 어드민 계정 생성
        memberService.createAdmin();

        //카테고리 데이터 추가
        Category category1 = new Category("스마트폰");
        Category category2 = new Category("노트북");
        Category savedCategory1 = categoryService.create(category1);
        Category savedCategory2 = categoryService.create(category2);

        //전자제품 데이터 추가
        ElectronicDevice device1 = new ElectronicDevice("갤럭시", savedCategory1);
        ElectronicDevice device2 = new ElectronicDevice("아이폰", savedCategory1);
        ElectronicDevice device3 = new ElectronicDevice("픽셀", savedCategory1);
        deviceService.create(device1);
        deviceService.create(device2);
        deviceService.create(device3);

        ElectronicDevice device4 = new ElectronicDevice("갤럭시북", savedCategory2);
        ElectronicDevice device5 = new ElectronicDevice("맥북", savedCategory2);
        ElectronicDevice device6 = new ElectronicDevice("그램", savedCategory2);
        deviceService.create(device4);
        deviceService.create(device5);
        deviceService.create(device6);
    }
}
