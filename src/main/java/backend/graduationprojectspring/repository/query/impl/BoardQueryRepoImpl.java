package backend.graduationprojectspring.repository.query.impl;

import backend.graduationprojectspring.entity.Board;
import backend.graduationprojectspring.repository.dto.PreviewBoardDto;
import backend.graduationprojectspring.repository.query.BoardQueryRepo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static backend.graduationprojectspring.entity.QBoard.board;
import static backend.graduationprojectspring.entity.QImage.image;
import static backend.graduationprojectspring.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class BoardQueryRepoImpl implements BoardQueryRepo {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<PreviewBoardDto> paging(int page, int size, Long deviceId){
        return queryFactory
                .select(Projections.constructor(
                        PreviewBoardDto.class,
                        board.id,
                        board.title,
                        board.member.name,
                        board.view,
                        board.createdTime
                ))
                .from(board)
                .join(board.member, member)
                .where(board.electronicDevice.id.eq(deviceId))
                .orderBy(board.id.desc())
                .offset((long) (page - 1) * size)
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
                .leftJoin(board.imageList, image)
                .fetchJoin()
                .where(board.id.eq(boardId))
                .fetchOne();

        return Optional.ofNullable(fetchBoard);
    }
}
