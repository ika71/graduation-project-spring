package backend.graduationprojectspring.entity;

import backend.graduationprojectspring.entity.Base.Base;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Evaluation extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "evaluation_id")
    private Long id;

    @Column(nullable = false)
    @Min(1) @Max(5)
    private int evaluationScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluation_item_id", nullable = false)
    private EvaluationItem evaluationItem;

    public Evaluation(int evaluationScore) {
        this.evaluationScore = evaluationScore;
    }

    public void setEvaluationItem(EvaluationItem evaluationItem) {
        this.evaluationItem = evaluationItem;
    }

    public void updateScore(int evaluationScore) {
        this.evaluationScore = evaluationScore;
    }
}
