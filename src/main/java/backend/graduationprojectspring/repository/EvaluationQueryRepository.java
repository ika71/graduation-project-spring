package backend.graduationprojectspring.repository;

import backend.graduationprojectspring.entity.Evaluation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static backend.graduationprojectspring.entity.QEvaluation.evaluation;

@Repository
@RequiredArgsConstructor
public class EvaluationQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<Evaluation> findByMemberIdEvalItemIds(String memberId, List<Long> evalItemIdList){
        return queryFactory
                .selectFrom(evaluation)
                .where(evaluation.createdBy.eq(memberId)
                        .and(evaluation.evaluationItem.id.in(evalItemIdList)))
                .fetch();
    }
}
