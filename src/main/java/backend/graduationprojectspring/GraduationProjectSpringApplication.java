package backend.graduationprojectspring;

import backend.graduationprojectspring.service.MemberService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class GraduationProjectSpringApplication {
    private final MemberService memberService;

    public static void main(String[] args) {
        SpringApplication.run(GraduationProjectSpringApplication.class, args);
    }
    /**
     * 테스트용 어드민 계정 생성
     */
    @PostConstruct
    public void post(){
        memberService.createAdmin();
    }
}
