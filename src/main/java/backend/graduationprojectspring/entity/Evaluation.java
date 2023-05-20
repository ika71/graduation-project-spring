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
    private EvaluationScore evaluationScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluation_item_id", nullable = false)
    private EvaluationItem evaluationItem;
}
