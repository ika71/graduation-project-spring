package backend.graduationprojectspring.service.impl;

import backend.graduationprojectspring.entity.Board;
import backend.graduationprojectspring.entity.BoardComment;
import backend.graduationprojectspring.entity.Member;
import backend.graduationprojectspring.exception.HttpError;
import backend.graduationprojectspring.repository.BoardCommentRepo;
import backend.graduationprojectspring.repository.BoardRepo;
import backend.graduationprojectspring.repository.MemberRepo;
import backend.graduationprojectspring.repository.query.BoardCommentQueryRepo;
import backend.graduationprojectspring.service.BoardCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardCommentServiceImpl implements BoardCommentService {
    private final BoardCommentRepo boardCommentRepo;
    private final BoardCommentQueryRepo boardCommentQueryRepo;
    private final BoardRepo boardRepo;
    private final MemberRepo memberRepo;
    @Override
    public List<BoardComment> paging(int page, int size, Long boardId) {
        return boardCommentQueryRepo.paging(page, size, boardId);
    }

    @Override
    public long totalCountByBoardId(Long boardId) {
        return boardCommentRepo.countByBoardId(boardId);
    }

    @Override
    public BoardComment create(String comment, Long boardId, Long memberId) {
        Board boardProxy = boardRepo.getReferenceById(boardId);
        Member memberProxy = memberRepo.getReferenceById(memberId);

        BoardComment boardComment = new BoardComment(
                comment,
                boardProxy,
                memberProxy);
        return boardCommentRepo.save(boardComment);
    }

    @Override
    public void delete(Long id, Long requestMemberId) {
        BoardComment findComment = boardCommentRepo.findById(id)
                .orElseThrow(() -> new HttpError("존재 하지 않는 댓글 입니다.", HttpStatus.UNPROCESSABLE_ENTITY));

        if(!findComment.getMember().getId().equals(requestMemberId)){
            throw new HttpError("본인의 댓글만 삭제할 수 있습니다.", HttpStatus.FORBIDDEN);
        }
        boardCommentRepo.deleteById(id);
    }
}
