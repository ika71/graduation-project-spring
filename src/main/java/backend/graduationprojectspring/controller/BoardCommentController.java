package backend.graduationprojectspring.controller;

import backend.graduationprojectspring.entity.BoardComment;
import backend.graduationprojectspring.service.BoardCommentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/board/{id}/comment")
@RequiredArgsConstructor
public class BoardCommentController {
    private final BoardCommentService boardCommentService;

    @GetMapping
    public BoardCommentPagingResultDto boardCommentPaging(
            @RequestParam(name = "page", defaultValue = "1")int page,
            @RequestParam(name = "size", defaultValue = "10")int size,
            @PathVariable(name = "id")Long boardId){
        List<BoardComment> pagingCommentList = boardCommentService.paging(page, size, boardId);
        Long totalCountByBoardId = boardCommentService.totalCountByBoardId(boardId);

        return new BoardCommentPagingResultDto(
                pagingCommentList,
                totalCountByBoardId);
    }

    @Getter
    @ToString
    public static class BoardCommentPagingResultDto{
        private final List<BoardCommentPagingDto> boardCommentPagingDtoList;
        private final Long totalCount;

        public BoardCommentPagingResultDto(List<BoardComment> boardCommentList, Long totalCount) {
            this.boardCommentPagingDtoList = boardCommentList
                    .stream()
                    .map(BoardCommentPagingDto::new)
                    .toList();
            this.totalCount = totalCount;
        }
    }
    @Getter
    @ToString
    public static class BoardCommentPagingDto{
        private final String comment;
        private final String createdBy;
        private final LocalDateTime createdTime;

        public BoardCommentPagingDto(BoardComment boardComment) {
            this.comment = boardComment.getComment();
            this.createdBy = boardComment.getMember().getName();
            this.createdTime = boardComment.getCreatedTime();
        }
    }
}
