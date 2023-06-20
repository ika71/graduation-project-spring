package backend.graduationprojectspring;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.service.CategoryService;
import backend.graduationprojectspring.service.MemberService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class GraduationProjectSpringApplication {
    private final MemberService memberService;
    private final CategoryService categoryService;

    public static void main(String[] args) {
        SpringApplication.run(GraduationProjectSpringApplication.class, args);
    }

    @PostConstruct
    public void post(){
        //테스트용 어드민 계정 생성
        memberService.createAdmin();

        //카테고리 데이터 추가
        Category category1 = new Category("노트북");
        Category category2 = new Category("컴퓨터");
        Category category3 = new Category("스마트폰");
        categoryService.create(category1);
        categoryService.create(category2);
        categoryService.create(category3);
    }
}
