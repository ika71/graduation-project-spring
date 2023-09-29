package backend.graduationprojectspring.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 카테고리
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "electronicDeviceList")
public class Category extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; //카테고리 이름

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    private List<ElectronicDevice> electronicDeviceList = new ArrayList<>(); //카테고리에 속한 전자제품

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
