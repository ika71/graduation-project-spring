package backend.graduationprojectspring.postconstruct;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.entity.Member;
import backend.graduationprojectspring.repository.MemberRepo;
import backend.graduationprojectspring.service.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("prod")
@RequiredArgsConstructor
public class PostConstructProduction {
    private final MemberService memberService;
    private final MemberRepo memberRepo;
    private final CategoryService categoryService;
    private final ElectronicDeviceService deviceService;
    private final EvaluationItemService itemService;
    private final BoardService boardService;
    private final BoardCommentService commentService;
    @Value("${admin.email}")
    private String adminEmail;
    @Value("${admin.name}")
    private String adminName;
    @Value("${admin.password}")
    private String adminPassword;

    @PostConstruct
    public void post(){
        if(!memberRepo.existsByEmail(adminEmail) || !memberRepo.existsByName(adminName)){
            Member admin = Member.ofCreateAdmin(adminEmail, adminName, adminPassword);
            memberService.create(admin);
        }

        if(categoryService.totalCount() == 0 && deviceService.totalCount() == 0){
            //카테고리 데이터 추가
            Category savedCategory1 = categoryService.create("스마트폰");
            Category savedCategory2 = categoryService.create("노트북");

            //전자제품 데이터 추가
            List<ElectronicDevice> deviceList = new ArrayList<>();
            deviceList.add(deviceService.create("아이폰 15", savedCategory1.getId()));
            deviceList.add(deviceService.create("아이폰 14", savedCategory1.getId()));
            deviceList.add(deviceService.create("아이폰 13", savedCategory1.getId()));
            deviceList.add(deviceService.create("구글 픽셀 6", savedCategory1.getId()));
            deviceList.add(deviceService.create("구글 픽셀 7", savedCategory1.getId()));
            deviceList.add(deviceService.create("구글 픽셀 8", savedCategory1.getId()));
            deviceList.add(deviceService.create("갤럭시 S21", savedCategory1.getId()));
            deviceList.add(deviceService.create("갤럭시 S22", savedCategory1.getId()));
            deviceList.add(deviceService.create("갤럭시 S23", savedCategory1.getId()));

            deviceList.add(deviceService.create("갤럭시북", savedCategory2.getId()));
            deviceList.add(deviceService.create("맥북", savedCategory2.getId()));
            deviceList.add(deviceService.create("그램", savedCategory2.getId()));

            //평가 항목 추가
            for (ElectronicDevice device : deviceList) {
                itemService.create("가격", device.getId());
                itemService.create("디스플레이", device.getId());
                itemService.create("성능", device.getId());
                itemService.create("배터리", device.getId());
                itemService.create("발열", device.getId());
            }
        }
    }
}
