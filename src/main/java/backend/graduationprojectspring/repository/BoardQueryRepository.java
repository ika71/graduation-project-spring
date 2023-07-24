package backend.graduationprojectspring.repository;

import backend.graduationprojectspring.entity.Board;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static backend.graduationprojectspring.entity.QBoard.board;
import static backend.graduationprojectspring.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class BoardQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<Board> paging(int page, int size, Long deviceId){
        return queryFactory
                .selectFrom(board)
                .join(board.member, member)
                .fetchJoin()
                .where(board.electronicDevice.id.eq(deviceId))
                .orderBy(board.id.desc())
                .offset((long)(page - 1)*size)
                .limit(size)
                .fetch();
    }
}
