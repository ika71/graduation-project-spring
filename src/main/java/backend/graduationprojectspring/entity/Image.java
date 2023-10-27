package backend.graduationprojectspring.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 이미지
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Image extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Column(nullable = false, updatable = false)
    private String originName; //이미지의 원래 이름

    @Column(nullable = false, unique = true, updatable = false)
    private String storeName; //파일로 저장된 이미지의 이름

    public Image(String originName, String storeName) {
        this.originName = originName;
        this.storeName = storeName;
    }

}
