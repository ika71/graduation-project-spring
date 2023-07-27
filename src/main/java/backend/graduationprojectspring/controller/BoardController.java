package backend.graduationprojectspring.controller;

import backend.graduationprojectspring.entity.Board;
import backend.graduationprojectspring.service.BoardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        Long totalCount = boardService.totalCount();

        return new BoardPagingResultDto(pagingBoardList, totalCount);
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
}
