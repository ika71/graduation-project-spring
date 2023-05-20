package backend.graduationprojectspring.entity;

import backend.graduationprojectspring.constant.EvaluationScore;
import backend.graduationprojectspring.entity.Base.Base;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Evaluation extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "evaluation_id")
    private Long id;

    @Column(nullable = false)
    @Enumerated(value = EnumType.ORDINAL)
    private EvaluationScore evaluation;
}
