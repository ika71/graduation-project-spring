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
@ToString(exclude = {"evaluationItemList", "boardList", "image"})
public class ElectronicDevice extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "electronic_device_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; //전자제품 이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; //전자제품이 속한 카테고리

    @OneToOne(mappedBy = "electronicDevice", cascade = CascadeType.REMOVE)
    private Image image; //전자제품의 대표 이미지

    @OneToMany(mappedBy = "electronicDevice", cascade = CascadeType.REMOVE)
    private List<EvaluationItem> evaluationItemList = new ArrayList<>(); //전자제품의 평가항목

    @OneToMany(mappedBy = "electronicDevice", cascade = CascadeType.REMOVE)
    private List<Board> boardList = new ArrayList<>(); //전자제품에 달린 리뷰 글

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
}
