package backend.graduationprojectspring.entity;

import backend.graduationprojectspring.entity.Base.Base;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ElectronicDevice extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "electronic_device_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

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

    public void setImage(Image image) {
        this.image = image;
    }

    public void update(ElectronicDevice electronicDevice){
        this.name = electronicDevice.getName();
        this.category = electronicDevice.getCategory();
    }
}
