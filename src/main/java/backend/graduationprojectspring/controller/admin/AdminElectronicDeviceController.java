package backend.graduationprojectspring.controller.admin;

import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.entity.Image;
import backend.graduationprojectspring.service.ElectronicDeviceService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @GetMapping
    public DevicePagingResultDto devicePaging(
            @RequestParam(name = "page", defaultValue = "1")int page,
            @RequestParam(name = "size", defaultValue = "10")int size){
        List<ElectronicDevice> devicePagingList = deviceService.pagingJoinCategory(page, size);
        Long deviceTotalCount = deviceService.totalCount();
        return new DevicePagingResultDto(devicePagingList, deviceTotalCount);
    }
    @PostMapping
    public ResponseEntity<?> deviceCreate(@RequestBody @Validated DeviceCreateDto deviceCreateDto){
        ElectronicDevice createdDevice = deviceService.create(
                deviceCreateDto.toElectronicDevice(),
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
    @PatchMapping("/{id}")
    public ResponseEntity<?> deviceUpdate(
            @PathVariable(name = "id")Long id,
            @RequestBody @Validated DeviceUpdateDto deviceUpdateDto){
        deviceService.update(id,
                deviceUpdateDto.toElectronicDevice(),
                deviceUpdateDto.getCategoryId());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deviceDelete(@PathVariable(name = "id")Long id){
        deviceService.delete(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
    @Getter
    private static class DevicePagingResultDto{
        private final List<DevicePagingDto> devicePagingDtoList;
        private final Long totalCount;

        public DevicePagingResultDto(List<ElectronicDevice> devicePagingList, Long totalCount) {
            this.devicePagingDtoList = devicePagingList
                    .stream()
                    .map(DevicePagingDto::new)
                    .toList();
            this.totalCount = totalCount;
        }
    }

    @Getter
    private static class DevicePagingDto {
        private final Long id;
        private final String name;
        private final CategoryDto categoryDto;
        private final Long imageId;

        public DevicePagingDto(ElectronicDevice device) {
            this.id = device.getId();
            this.name = device.getName();
            this.categoryDto = new CategoryDto(
                    device.getCategory().getId(),
                    device.getCategory().getName());
            this.imageId = device.getImage()
                    .map(Image::getId)
                    .orElse(null);
        }
    }

    @Getter
    private static class CategoryDto {
        private final Long id;
        private final String name;

        public CategoryDto(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    @Getter
    private static class DeviceCreateDto {
        @NotNull
        private Long categoryId;
        @NotBlank
        private String name;

        public ElectronicDevice toElectronicDevice(){
            return new ElectronicDevice(name);
        }
    }

    @Getter
    private static class DeviceImageSetDto {
        @NotNull
        private Long imageId;
    }

    @Getter
    private static class DeviceUpdateDto {
        @NotBlank
        private String name;
        @NotNull
        private Long categoryId;

        public ElectronicDevice toElectronicDevice(){
            return new ElectronicDevice(name);
        }
    }
}
