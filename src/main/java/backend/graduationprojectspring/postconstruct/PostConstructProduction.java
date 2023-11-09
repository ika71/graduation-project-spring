package backend.graduationprojectspring.postconstruct;

import backend.graduationprojectspring.entity.Member;
import backend.graduationprojectspring.repository.MemberRepo;
import backend.graduationprojectspring.service.MemberService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
@RequiredArgsConstructor
public class PostConstructProduction {
    private final MemberService memberService;
    private final MemberRepo memberRepo;
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
    }
}
