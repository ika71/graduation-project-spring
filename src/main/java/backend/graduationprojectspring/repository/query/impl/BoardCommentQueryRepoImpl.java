package backend.graduationprojectspring.repository.query.impl;

import backend.graduationprojectspring.entity.BoardComment;
import backend.graduationprojectspring.repository.query.BoardCommentQueryRepo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static backend.graduationprojectspring.entity.QBoardComment.boardComment;
import static backend.graduationprojectspring.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class BoardCommentQueryRepoImpl implements BoardCommentQueryRepo {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<BoardComment> paging(int page, int size, Long boardId) {
        return queryFactory
                .selectFrom(boardComment)
                .join(boardComment.member, member)
                .fetchJoin()
                .where(boardComment.board.id.eq(boardId))
                .offset((long)(page - 1)*size)
                .limit(size)
                .fetch();
    }
}
