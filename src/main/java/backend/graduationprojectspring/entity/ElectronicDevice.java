package backend.graduationprojectspring.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 전자제품
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"evaluationItemList"})
public class ElectronicDevice extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "electronic_device_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; //전자제품 이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @OneToMany(mappedBy = "electronicDevice")
    private List<EvaluationItem> evaluationItemList = new ArrayList<>();

    public ElectronicDevice(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public Optional<Image> getImage() {
        return Optional.ofNullable(image);
    }

    public void updateNameAndCategory(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
