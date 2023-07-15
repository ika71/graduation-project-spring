package backend.graduationprojectspring.entity;

import backend.graduationprojectspring.entity.Base.Base;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Evaluation extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "evaluation_id")
    private Long id;

    @Column(nullable = false)
    @Min(1) @Max(5)
    private Integer evaluationScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluation_item_id", nullable = false)
    private EvaluationItem evaluationItem;

    public Evaluation(Integer evaluationScore, EvaluationItem evaluationItem) {
        this.evaluationScore = evaluationScore;
        this.evaluationItem = evaluationItem;
    }

    public void updateScore(Integer evaluationScore) {
        this.evaluationScore = evaluationScore;
    }
}
