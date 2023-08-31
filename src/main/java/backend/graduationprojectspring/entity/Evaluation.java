package backend.graduationprojectspring.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * 평점
 */
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
    private Integer evaluationScore; //평점 점수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluation_item_id", nullable = false)
    private EvaluationItem evaluationItem; //평점 대상이 되는 평가 요소

    public Evaluation(Integer evaluationScore, EvaluationItem evaluationItem) {
        this.evaluationScore = evaluationScore;
        this.evaluationItem = evaluationItem;
    }

    /**
     * 평점 점수를 수정한다.
     * @param evaluationScore 수정 후의 평점 점수
     */
    public void updateScore(Integer evaluationScore) {
        this.evaluationScore = evaluationScore;
    }
}
