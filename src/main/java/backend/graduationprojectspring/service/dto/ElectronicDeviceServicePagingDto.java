package backend.graduationprojectspring.service.dto;

import backend.graduationprojectspring.entity.ElectronicDevice;
import lombok.Getter;

import java.util.List;

@Getter
public class ElectronicDeviceServicePagingDto {
    private final List<ElectronicDevice> pagingDeviceList;
    private final Long totalCount;

    public ElectronicDeviceServicePagingDto(List<ElectronicDevice> deviceList, Long totalCount) {
        this.pagingDeviceList = deviceList;
        this.totalCount = totalCount;
    }
}
