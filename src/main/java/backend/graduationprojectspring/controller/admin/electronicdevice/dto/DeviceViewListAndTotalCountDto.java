package backend.graduationprojectspring.controller.admin.electronicdevice.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class DeviceViewListAndTotalCountDto {
    private List<DeviceViewDto> deviceViewDtoList;
    private Long totalCount;

    public DeviceViewListAndTotalCountDto(List<DeviceViewDto> deviceViewDto, Long totalCount) {
        this.deviceViewDtoList = deviceViewDto;
        this.totalCount = totalCount;
    }
}
