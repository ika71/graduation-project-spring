package backend.graduationprojectspring.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EvaluationItem extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "evaluation_item_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "electronic_device_id", nullable = false)
    private ElectronicDevice electronicDevice;

    public EvaluationItem(String name, ElectronicDevice electronicDevice) {
        this.name = name;
        this.electronicDevice = electronicDevice;
        electronicDevice.getEvaluationItemList().add(this);
    }

    public void updateName(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return "EvaluationItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", electronicDevice=" + electronicDevice.getId() +
                " " + electronicDevice.getName() +
                '}';
    }
}
