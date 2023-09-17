package backend.graduationprojectspring.controller;

import backend.graduationprojectspring.entity.Board;
import backend.graduationprojectspring.entity.Image;
import backend.graduationprojectspring.repository.dto.PreviewBoardDto;
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

import java.time.format.DateTimeFormatter;
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
        List<PreviewBoardDto> pagingBoardList = boardService.paging(page, size, deviceId);
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
            @RequestParam(name = "deviceId")Long deviceId,
            @RequestBody @Validated BoardCreateDto boardCreateDto){
        String memberId = SecurityContextHolder.getContext().getAuthentication().getName();

        boardService.create(
                boardCreateDto.getTitle(),
                boardCreateDto.getContent(),
                deviceId,
                Long.valueOf(memberId),
                boardCreateDto.getImageIdList());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> boardUpdate(
            @PathVariable(name = "id")Long id,
            @RequestBody @Validated BoardUpdateDto boardUpdateDto){
        String memberId = SecurityContextHolder.getContext().getAuthentication().getName();

        boardService.update(id,
                boardUpdateDto.getTitle(),
                boardUpdateDto.getContent(),
                Long.valueOf(memberId),
                boardUpdateDto.getAddImageIdList(),
                boardUpdateDto.getDeleteImageIdList());

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> boardDelete(
            @PathVariable(name = "id")Long id){
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
        private final Long totalCount;

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
        private final List<Long> imageList;
        private final long view;

        public BoardDetailDto(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.createdBy = board.getMember().getName();
            this.createdTime = board.getCreatedTime()
                    .format(DateTimeFormatter.ofPattern("MM-dd / HH:mm"));
            this.imageList = board.getImageList()
                    .stream()
                    .map(Image::getId)
                    .toList();
            this.view = board.getView();
        }
    }
    @Getter
    @ToString
    public static class BoardCreateDto{
        @NotBlank
        private String title;
        @NotBlank
        private String content;
        private List<Long> imageIdList;
    }
    @Getter
    @ToString
    public static class BoardUpdateDto{
        @NotBlank
        private String title;
        @NotBlank
        private String content;
        private List<Long> addImageIdList;
        private List<Long> deleteImageIdList;
    }
}
