package backend.graduationprojectspring.controller.admin.electronicdevice;

import backend.graduationprojectspring.controller.admin.electronicdevice.dto.DeviceViewDto;
import backend.graduationprojectspring.controller.admin.electronicdevice.dto.DeviceViewListAndTotalCountDto;
import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.service.ElectronicDeviceService;
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
    public DeviceViewListAndTotalCountDto pagingDevice(
            @RequestParam(name = "page", defaultValue = "1")int page,
            @RequestParam(name = "size", defaultValue = "10")int size
    ){
        List<ElectronicDevice> deviceList = deviceService.paging(page, size);
        Long deviceTotalCount = deviceService.totalCount();

        List<DeviceViewDto> deviceViewDtoList = deviceList
                .stream()
                .map(DeviceViewDto::new)
                .toList();

        return new DeviceViewListAndTotalCountDto(deviceViewDtoList, deviceTotalCount);
    }
}
