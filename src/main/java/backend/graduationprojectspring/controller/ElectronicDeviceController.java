package backend.graduationprojectspring.controller;

import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.entity.EvaluationItem;
import backend.graduationprojectspring.entity.Image;
import backend.graduationprojectspring.service.ElectronicDeviceService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/device")
@RequiredArgsConstructor
public class ElectronicDeviceController {
    private final ElectronicDeviceService deviceService;
    @GetMapping
    public PagingResult electronicDevicePaging(
            @RequestParam(name = "page", defaultValue = "1")int page,
            @RequestParam(name = "size", defaultValue = "10")int size){
        List<ElectronicDevice> deviceList = deviceService.pagingJoinCategoryAndDevice(page, size);
        Long totalCount = deviceService.totalCount();

        List<DevicePagingDto> devicePagingDtoList = new ArrayList<>(deviceList.size());
        deviceList.forEach(device -> {
            List<String> evalItemNameList = changeEvalItemNameList(device.getEvaluationItemList());
            devicePagingDtoList.add(new DevicePagingDto(device, evalItemNameList));
        });
        return new PagingResult(devicePagingDtoList, totalCount);
    }

    /**
     * evaluationItemList의 evaluation name들을 모아서 List로 반환
     * @param itemList
     * @return
     */
    private List<String> changeEvalItemNameList(List<EvaluationItem> itemList){
        return itemList
                .stream()
                .map(EvaluationItem::getName)
                .toList();
    }

    @Getter
    private static class PagingResult{
        private final List<DevicePagingDto> devicePagingDtoList;
        private final Long totalCount;

        public PagingResult(List<DevicePagingDto> devicePagingDtoList, Long totalCount) {
            this.devicePagingDtoList = devicePagingDtoList;
            this.totalCount = totalCount;
        }
    }

    @Getter
    private static class DevicePagingDto {
        private final Long id;
        private final String name;
        private final String categoryName;
        private final Long imageId;
        private final LocalDateTime createdTime;
        private final List<String> evaluationItemList;

        public DevicePagingDto(ElectronicDevice device, List<String> evaluationItemList) {
            this.id = device.getId();
            this.name = device.getName();
            this.categoryName = device.getCreatedBy();
            this.imageId = Optional.ofNullable(device.getImage())
                    .map(Image::getId)
                    .orElse(null);
            this.createdTime = device.getCreatedTime();
            this.evaluationItemList = evaluationItemList;
        }
    }
}
