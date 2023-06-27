package backend.graduationprojectspring.controller.admin.electronicdevice;

import backend.graduationprojectspring.controller.admin.electronicdevice.dto.DeviceCreateDto;
import backend.graduationprojectspring.controller.admin.electronicdevice.dto.DevicePagingDto;
import backend.graduationprojectspring.controller.admin.electronicdevice.dto.DeviceUpdateDto;
import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.service.ElectronicDeviceService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping
    public ResponseEntity<?> deviceCreate(@RequestBody @Validated DeviceCreateDto deviceCreateDto){
        ElectronicDevice device = deviceCreateDto.toElectronicDevice();
        ElectronicDevice createdDevice = deviceService.create(device);
        return ResponseEntity.ok().body(createdDevice.getName());

    }
    @PatchMapping
    public ResponseEntity<?> deviceUpdate(@RequestBody @Validated DeviceUpdateDto deviceUpdateDto){
        deviceService.update(deviceUpdateDto.getId(),
                deviceUpdateDto.toElectronicDevice());
        return ResponseEntity.ok().body("");
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
