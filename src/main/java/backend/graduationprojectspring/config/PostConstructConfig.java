package backend.graduationprojectspring.config;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.entity.Member;
import backend.graduationprojectspring.service.BoardCommentService;
import backend.graduationprojectspring.service.BoardService;
import backend.graduationprojectspring.service.impl.CategoryServiceImpl;
import backend.graduationprojectspring.service.impl.ElectronicDeviceServiceImpl;
import backend.graduationprojectspring.service.impl.EvaluationItemServiceImpl;
import backend.graduationprojectspring.service.impl.MemberServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class PostConstructConfig {
    private final MemberServiceImpl memberService;
    private final CategoryServiceImpl categoryService;
    private final ElectronicDeviceServiceImpl deviceService;
    private final EvaluationItemServiceImpl itemService;
    private final BoardService boardService;
    private final BoardCommentService commentService;

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
        itemService.create("가격", device1.getId());
        itemService.create("디스플레이", device1.getId());
        itemService.create("성능", device1.getId());

        //테스트용 게시글
        boardService.create("test", "test", 1L, 1L, null);
        //테스트용 댓글
        commentService.create("댓글1", 1L, 1L);
        commentService.create("댓글2", 1L, 1L);
        commentService.create("댓글3", 1L, 1L);
    }
}
