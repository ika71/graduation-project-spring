package backend.graduationprojectspring.controller.electronic_device.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class DevicePagingDto {
    private Long id;
    private String name;
    private String categoryName;
    private Long imageId;
    private LocalDateTime createdTime;
    private List<String> evaluationItemList;
}
