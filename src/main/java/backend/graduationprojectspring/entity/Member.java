package backend.graduationprojectspring.entity;

import backend.graduationprojectspring.constant.Role;
import backend.graduationprojectspring.entity.Base.Base;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    /**
     * 로그인아이디
     */
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String name;

    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public Member(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = Role.USER;
    }
}
