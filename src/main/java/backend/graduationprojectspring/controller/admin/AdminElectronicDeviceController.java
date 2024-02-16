package backend.graduationprojectspring.controller.admin;

import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.entity.Image;
import backend.graduationprojectspring.service.ElectronicDeviceService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
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

    @GetMapping
    public DevicePagingResultDto devicePaging(
            @RequestParam(defaultValue = "1")int page,
            @RequestParam(defaultValue = "10")int size){
        List<ElectronicDevice> devicePagingList = deviceService.pagingJoinCategory(page, size);
        long deviceTotalCount = deviceService.totalCount();
        return new DevicePagingResultDto(devicePagingList, deviceTotalCount);
    }
    @PostMapping
    public ResponseEntity<?> deviceCreate(@RequestBody @Validated DeviceCreateDto deviceCreateDto){
        ElectronicDevice createdDevice = deviceService.create(
                deviceCreateDto.getName(),
                deviceCreateDto.getCategoryId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdDevice.getName());
    }
    @PostMapping("/{id}/image") ResponseEntity<?> deviceImageSet(
            @PathVariable(name = "id")Long deviceId,
            @RequestBody @Validated DeviceImageSetDto deviceImageSetDto){
        deviceService.setImage(deviceId, deviceImageSetDto.getImageId());

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> deviceUpdate(
            @PathVariable Long id,
            @RequestBody @Validated DeviceUpdateDto deviceUpdateDto){
        deviceService.update(id,
                deviceUpdateDto.getName(),
                deviceUpdateDto.getCategoryId());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deviceDelete(@PathVariable Long id){
        deviceService.delete(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
    @Getter
    @ToString
    public static class DevicePagingResultDto{
        private final List<DevicePagingDto> deviceList;
        private final long totalCount;

        public DevicePagingResultDto(List<ElectronicDevice> devicePagingList, Long totalCount) {
            this.deviceList = devicePagingList
                    .stream()
                    .map(DevicePagingDto::new)
                    .toList();
            this.totalCount = totalCount;
        }
    }

    @Getter
    @ToString
    public static class DevicePagingDto {
        private final Long id;
        private final String name;
        private final CategoryDto category;
        private final Long imageId;

        public DevicePagingDto(ElectronicDevice device) {
            this.id = device.getId();
            this.name = device.getName();
            this.category = new CategoryDto(
                    device.getCategory().getId(),
                    device.getCategory().getName());
            this.imageId = device.getImage()
                    .map(Image::getId)
                    .orElse(null);
        }
    }

    @Getter
    @ToString
    public static class CategoryDto {
        private final Long id;
        private final String name;

        public CategoryDto(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    @Getter
    @ToString
    public static class DeviceCreateDto {
        @NotNull
        private Long categoryId;
        @NotBlank
        private String name;
    }

    @Getter
    @ToString
    public static class DeviceImageSetDto {
        @NotNull
        private Long imageId;
    }

    @Getter
    @ToString
    public static class DeviceUpdateDto {
        @NotBlank
        private String name;
        @NotNull
        private Long categoryId;
    }
}
