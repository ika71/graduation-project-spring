package backend.graduationprojectspring.controller.admin.electronicdevice;

import backend.graduationprojectspring.controller.admin.electronicdevice.dto.DevicePagingDto;
import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.service.ElectronicDeviceService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/device")
@RequiredArgsConstructor
public class AdminElectronicDeviceController {
    private final ElectronicDeviceService deviceService;

    @GetMapping
    public DevicePagingResultDto pagingDevice(
            @RequestParam(name = "page", defaultValue = "1")int page,
            @RequestParam(name = "size", defaultValue = "10")int size
    ){
        List<ElectronicDevice> deviceList = deviceService.paging(page, size);
        Long deviceTotalCount = deviceService.totalCount();

        List<DevicePagingDto> devicePagingDtoList = deviceList
                .stream()
                .map(DevicePagingDto::new)
                .toList();

        return new DevicePagingResultDto(devicePagingDtoList, deviceTotalCount);
    }

    @Getter
    public static class DevicePagingResultDto{
        private final List<DevicePagingDto> devicePagingDtoList;
        private final Long totalCount;

        public DevicePagingResultDto(List<DevicePagingDto> devicePagingDto, Long totalCount) {
            this.devicePagingDtoList = devicePagingDto;
            this.totalCount = totalCount;
        }
    }
}
