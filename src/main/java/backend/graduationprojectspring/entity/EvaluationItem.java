package backend.graduationprojectspring.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 평가요소
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "evaluationList")
public class EvaluationItem extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "evaluation_item_id")
    private Long id;

    @Column(nullable = false)
    private String name; //평가요소 이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "electronic_device_id", nullable = false)
    private ElectronicDevice electronicDevice; //평가요소가 속해 있는 전자제품

    @OneToMany(mappedBy = "evaluationItem", cascade = CascadeType.REMOVE)
    private List<Evaluation> evaluationList = new ArrayList<>(); //평가항목에 달린 평점

    public EvaluationItem(String name, ElectronicDevice electronicDevice) {
        this.name = name;
        this.electronicDevice = electronicDevice;
        electronicDevice.getEvaluationItemList().add(this);
    }

    /**
     * 평가요소 이름을 변경한다.
     * @param name 변경 후의 이름
     */
    public void updateName(String name){
        this.name = name;
    }

}
