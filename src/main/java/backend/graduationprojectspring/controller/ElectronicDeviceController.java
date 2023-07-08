package backend.graduationprojectspring.controller;

import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.entity.EvaluationItem;
import backend.graduationprojectspring.entity.Image;
import backend.graduationprojectspring.service.ElectronicDeviceService;
import lombok.Builder;
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
    public PagingResult paging(
            @RequestParam(name = "page", defaultValue = "1")int page,
            @RequestParam(name = "size", defaultValue = "10")int size){

        List<ElectronicDevice> pagingDevice = deviceService.paging(page, size);
        List<ElectronicDevice> deviceList = deviceService.fetchJoinEvaluationItem(pagingDevice);
        Long totalCount = deviceService.totalCount();

        List<DevicePagingDto> devicePagingDtoList = new ArrayList<>(deviceList.size());
        for (ElectronicDevice device : deviceList) {
            List<String> evalItemNameList = changeEvalItemNameList(device.getEvaluationItemList());

            DevicePagingDto devicePagingDto = DevicePagingDto
                    .builder()
                    .id(device.getId())
                    .name(device.getName())
                    .imageId(getImageId(device.getImage()))
                    .categoryName(device.getCategory().getName())
                    .createdTime(device.getCreatedTime())
                    .evaluationItemList(evalItemNameList)
                    .build();

            devicePagingDtoList.add(devicePagingDto);
        }
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

    /**
     * NPE처리 image가 null이면 그대로 null 반환
     * image가 null이 아니면 image의 imageId 반환
     * @param image
     * @return
     */
    private Long getImageId(Image image){
        return Optional.ofNullable(image)
                .map(Image::getId)
                .orElse(null);
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
    @Builder
    private static class DevicePagingDto {
        private Long id;
        private String name;
        private String categoryName;
        private Long imageId;
        private LocalDateTime createdTime;
        private List<String> evaluationItemList;
    }
}
