package backend.graduationprojectspring.controller.admin.electronic_device;

import backend.graduationprojectspring.controller.admin.electronic_device.dto.DeviceCreateDto;
import backend.graduationprojectspring.controller.admin.electronic_device.dto.DevicePagingDto;
import backend.graduationprojectspring.controller.admin.electronic_device.dto.DeviceUpdateDto;
import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.service.CategoryService;
import backend.graduationprojectspring.service.ElectronicDeviceService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/device")
@RequiredArgsConstructor
public class AdminElectronicDeviceController {
    private final ElectronicDeviceService deviceService;
    private final CategoryService categoryService;

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
        Category category = categoryService.getReferenceById(deviceCreateDto.getCategoryId());
        ElectronicDevice device = deviceCreateDto.toElectronicDevice(category);
        ElectronicDevice createdDevice = deviceService.create(device);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdDevice.getName());
    }
    @PatchMapping
    public ResponseEntity<?> deviceUpdate(@RequestBody @Validated DeviceUpdateDto deviceUpdateDto){
        Category category = categoryService.getReferenceById(deviceUpdateDto.getCategoryId());

        deviceService.update(deviceUpdateDto.getId(),
                deviceUpdateDto.toElectronicDevice(category));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
    @DeleteMapping
    public ResponseEntity<?> deviceDelete(@RequestParam(name = "id")Long id){
        deviceService.delete(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
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