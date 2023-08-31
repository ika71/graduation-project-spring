package backend.graduationprojectspring.entity;

import backend.graduationprojectspring.constant.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 회원 정보
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "password")
public class Member extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String email; //로그인 아이디로 사용되는 이메일

    @Column(nullable = false, unique = true)
    private String name; //회원 닉네임

    private String password; //회원 비밀번호

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role; //회원 권한

    /**
     * 일반 유저를 생성한다.
     * @param email 회원의 로그인 아이디
     * @param name 회원의 닉네임
     * @param password 회원의 비밀번호
     * @return 새로 생성된 회원
     */
    public static Member of(String email, String name, String password){
        return new Member(email, name, password, Role.USER);
    }

    /**
     * ADMIN 권한을 가진 회원을 생성한다.
     * @param email 회원의 로그인 아이디
     * @param name 회원의 닉네임
     * @param password 회원의 비밀번호
     * @return 새로 생성된 회원
     */
    public static Member ofCreateAdmin(String email, String name, String password){
        return new Member(email, name, password, Role.ADMIN);
    }

    private Member(String email, String name, String password, Role role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    /**
     * 회원의 비밀번호를 암호화 한다.
     * @param passwordEncoder 암호화에 사용되는 PasswordEncoder
     */
    public void passwordEncode(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(this.password);
    }
}
