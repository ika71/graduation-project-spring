package backend.graduationprojectspring.controller;

import backend.graduationprojectspring.entity.Board;
import backend.graduationprojectspring.service.BoardService;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping
    public BoardPagingResultDto boardPaging(
            @RequestParam(name = "page", defaultValue = "1")int page,
            @RequestParam(name = "size", defaultValue = "10")int size,
            @RequestParam(name = "deviceId")Long deviceId){
        List<Board> pagingBoardList = boardService.paging(page, size, deviceId);
        Long totalCount = boardService.totalCountByDeviceId(deviceId);

        return new BoardPagingResultDto(pagingBoardList, totalCount);
    }
    @GetMapping("/{id}")
    public BoardDetailDto boardDetail(
            @PathVariable(name = "id")Long id){
        Board findBoard = boardService.findOneDetail(id);
        return new BoardDetailDto(findBoard);
    }
    @PostMapping
    public ResponseEntity<?> boardCreate(
            @RequestBody @Validated BoardCreateDto boardCreateDto){
        String memberId = SecurityContextHolder.getContext().getAuthentication().getName();

        boardService.create(
                boardCreateDto.getTitle(),
                boardCreateDto.getContent(),
                Long.valueOf(memberId));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> boardUpdate(
            @PathVariable(name = "id")Long id,
            @RequestBody @Validated BoardUpdateDto boardUpdateDto){

        boardService.update(id,
                boardUpdateDto.getTitle(),
                boardUpdateDto.getContent());

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> boardDelete(
            @PathVariable(name = "id")Long id){
        boardService.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Getter
    @ToString
    public static class BoardPagingResultDto{
        private final List<BoardPagingDto> boardPagingDtoList;
        private final Long totalCount;

        public BoardPagingResultDto(List<Board> boardList, Long totalCount) {
            this.boardPagingDtoList = boardList
                    .stream()
                    .map(BoardPagingDto::new)
                    .toList();
            this.totalCount = totalCount;
        }
    }

    @Getter
    @ToString
    private static class BoardPagingDto{
        private final Long id;
        private final String title;
        private final String content;
        private final String nickName;
        private final String createdTime;

        public BoardPagingDto(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.nickName = board.getMember().getName();
            this.createdTime = board.getCreatedTime().toString();
        }
    }
    @Getter
    @ToString
    public static  class BoardDetailDto{
        private final Long id;
        private final String title;
        private final String content;
        private final String createdBy;
        private final LocalDateTime createdTime;

        public BoardDetailDto(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.createdBy = board.getMember().getName();
            this.createdTime = board.getCreatedTime();
        }
    }
    @Getter
    @ToString
    public static class BoardCreateDto{
        @NotBlank
        private String title;
        @NotBlank
        private String content;
    }
    @Getter
    @ToString
    public static class BoardUpdateDto{
        @NotBlank
        private String title;
        @NotBlank
        private String content;
    }
}
