package backend.graduationprojectspring.entity;

import backend.graduationprojectspring.entity.Base.Base;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Image extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "image_id")
    private Long id;

    @Column(nullable = false, updatable = false)
    private String originName;

    @Column(nullable = false, unique = true, updatable = false)
    private String storeName;

    public Image(String originName, String storeName) {
        this.originName = originName;
        this.storeName = storeName;
    }
}
