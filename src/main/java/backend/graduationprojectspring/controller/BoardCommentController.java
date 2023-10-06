package backend.graduationprojectspring.controller;

import backend.graduationprojectspring.entity.BoardComment;
import backend.graduationprojectspring.service.BoardCommentService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
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
        long totalCountByBoardId = boardCommentService.totalCountByBoardId(boardId);

        return new BoardCommentPagingResultDto(
                pagingCommentList,
                totalCountByBoardId);
    }
    @PostMapping
    public ResponseEntity<?> boardCommentCreate(
            @PathVariable(name = "id")Long boardId,
            @RequestBody @Validated  BoardCommentCreateDto boardCommentCreateDto){
        String memberId = SecurityContextHolder.getContext().getAuthentication().getName();

        boardCommentService.create(
                boardCommentCreateDto.getComment(),
                boardId,
                Long.valueOf(memberId));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> boardCommentDelete(
            @PathVariable(name = "id")Long boardId,
            @PathVariable(name = "commentId")Long commentId){

        String memberId = SecurityContextHolder.getContext().getAuthentication().getName();

        boardCommentService.delete(commentId, Long.valueOf(memberId));

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
    @Getter
    @ToString
    public static class BoardCommentPagingResultDto{
        private final List<BoardCommentPagingDto> boardCommentList;
        private final long totalCount;

        public BoardCommentPagingResultDto(List<BoardComment> boardCommentList, Long totalCount) {
            this.boardCommentList = boardCommentList
                    .stream()
                    .map(BoardCommentPagingDto::new)
                    .toList();
            this.totalCount = totalCount;
        }
    }
    @Getter
    @ToString
    public static class BoardCommentPagingDto{
        private final Long id;
        private final String comment;
        private final String createdBy;
        private final String createdTime;

        public BoardCommentPagingDto(BoardComment boardComment) {
            this.id = boardComment.getId();
            this.comment = boardComment.getComment();
            this.createdBy = boardComment.getMember().getName();
            this.createdTime = boardComment.getCreatedTime()
                    .format(DateTimeFormatter.ofPattern("MM-dd / HH:mm"));
        }
    }
    @Getter
    @ToString
    public static class BoardCommentCreateDto{
        @NotBlank
        @Size(max = 255, message = "댓글은 255자 이상을 넘을 수 없습니다.")
        private String comment;
    }
}
