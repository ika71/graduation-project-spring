package backend.graduationprojectspring.controller;

import backend.graduationprojectspring.entity.Board;
import backend.graduationprojectspring.repository.dto.PreviewBoardDto;
import backend.graduationprojectspring.service.BoardService;
import jakarta.validation.constraints.Max;
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
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping
    public BoardPagingResultDto boardPaging(
            @RequestParam(defaultValue = "1")int page,
            @RequestParam(defaultValue = "10") @Max(50) int size,
            @RequestParam Long deviceId){
        List<PreviewBoardDto> pagingBoardList = boardService.paging(page, size, deviceId);
        long totalCount = boardService.totalCountByDeviceId(deviceId);

        return new BoardPagingResultDto(pagingBoardList, totalCount);
    }
    @GetMapping("/{id}")
    public BoardDetailDto boardDetail(
            @PathVariable Long id){
        Board findBoard = boardService.findOneDetail(id);
        return new BoardDetailDto(findBoard);
    }
    @PostMapping
    public ResponseEntity<?> boardCreate(
            @RequestParam Long deviceId,
            @RequestBody @Validated BoardCreateDto boardCreateDto){
        String memberId = SecurityContextHolder.getContext().getAuthentication().getName();

        boardService.create(
                boardCreateDto.getTitle(),
                boardCreateDto.getContent(),
                deviceId,
                Long.valueOf(memberId));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> boardUpdate(
            @PathVariable Long id,
            @RequestBody @Validated BoardUpdateDto boardUpdateDto){
        String memberId = SecurityContextHolder.getContext().getAuthentication().getName();

        boardService.update(id,
                boardUpdateDto.getTitle(),
                boardUpdateDto.getContent(),
                Long.valueOf(memberId));

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> boardDelete(
            @PathVariable Long id){
        String memberId = SecurityContextHolder.getContext().getAuthentication().getName();
        boardService.delete(id, Long.valueOf(memberId));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Getter
    @ToString
    public static class BoardPagingResultDto{
        private final List<BoardPagingDto> boardList;
        private final long totalCount;

        public BoardPagingResultDto(List<PreviewBoardDto> boardList, Long totalCount) {
            this.boardList = boardList
                    .stream()
                    .map(BoardPagingDto::new)
                    .toList();
            this.totalCount = totalCount;
        }
    }

    @Getter
    @ToString
    public static class BoardPagingDto{
        private final Long id;
        private final String title;
        private final String nickName;
        private final long view;
        private final String createdTime;

        public BoardPagingDto(PreviewBoardDto previewBoardDto) {
            this.id = previewBoardDto.getId();
            this.title = previewBoardDto.getTitle();
            this.nickName = previewBoardDto.getNickName();
            this.view = previewBoardDto.getView();
            this.createdTime = previewBoardDto.getCreatedTime()
                    .format(DateTimeFormatter.ofPattern("MM-dd / HH:mm"));
        }
    }
    @Getter
    @ToString
    public static  class BoardDetailDto{
        private final Long id;
        private final String title;
        private final String content;
        private final String createdBy;
        private final String createdTime;
        private final long view;

        public BoardDetailDto(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.createdBy = board.getMember().getName();
            this.createdTime = board.getCreatedTime()
                    .format(DateTimeFormatter.ofPattern("MM-dd / HH:mm"));
            this.view = board.getView();
        }
    }
    @Getter
    @ToString
    public static class BoardCreateDto{
        @NotBlank
        @Size(max = 20, message = "제목은 20자를 넘을 수 없습니다.")
        private String title;
        @NotBlank
        private String content;
    }
    @Getter
    @ToString
    public static class BoardUpdateDto{
        @NotBlank
        @Size(max = 20, message = "제목은 20자를 넘을 수 없습니다.")
        private String title;
        @NotBlank
        private String content;
    }
}
