package backend.graduationprojectspring.repository.query.impl;

import backend.graduationprojectspring.entity.Board;
import backend.graduationprojectspring.repository.query.BoardQueryRepo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static backend.graduationprojectspring.entity.QBoard.board;
import static backend.graduationprojectspring.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class BoardQueryRepoImpl implements BoardQueryRepo {
    private final JPAQueryFactory queryFactory;

    /**
     * TODO 게시 글을 페이징 할 때 게시글 내용도 가져옴 최적화를 해야함
     * @param page
     * @param size
     * @param deviceId
     * @return
     */
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

    @Override
    public Long totalCountByDeviceId(Long deviceId) {
        return queryFactory
                .select(board.count())
                .from(board)
                .where(board.electronicDevice.id.eq(deviceId))
                .fetchOne();
    }

    @Override
    public Optional<Board> findOneDetail(Long boardId) {
        Board fetchBoard = queryFactory
                .selectFrom(board)
                .join(board.member, member)
                .fetchJoin()
                .where(board.id.eq(boardId))
                .fetchOne();

        return Optional.ofNullable(fetchBoard);
    }
}
