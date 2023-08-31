package backend.graduationprojectspring.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 카테고리
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Category extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; //카테고리 이름

    public Category(String name) {
        this.name = name;
    }

    /**
     * 카테고리 이름을 수정한다.
     * @param name 수정 후의 카테고리 이름
     */
    public void updateName(String name){
        this.name = name;
    }
}
