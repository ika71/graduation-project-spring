package backend.graduationprojectspring.repository.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PreviewBoardDto {
    private final Long id;
    private final String title;
    private final String nickName;
    private final long view;
    private final LocalDateTime createdTime;

    public PreviewBoardDto(Long id, String title, String nickName, long view, LocalDateTime createdTime) {
        this.id = id;
        this.title = title;
        this.nickName = nickName;
        this.view = view;
        this.createdTime = createdTime;
    }
}
