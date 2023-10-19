package backend.graduationprojectspring.controller;

import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.entity.EvaluationItem;
import backend.graduationprojectspring.entity.Image;
import backend.graduationprojectspring.service.ElectronicDeviceService;
import backend.graduationprojectspring.service.dto.DeviceDetailAndAvgDto;
import jakarta.validation.constraints.Max;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/device")
@RequiredArgsConstructor
public class ElectronicDeviceController {
    private final ElectronicDeviceService deviceService;
    @GetMapping
    public PagingResultDto electronicDevicePaging(
            @RequestParam(name = "page", defaultValue = "1")int page,
            @RequestParam(name = "size", defaultValue = "10") @Max(50) int size,
            @RequestParam(required = false) String nameCondition,
            @RequestParam(required = false) String categoryCondition){
        List<ElectronicDevice> deviceList = deviceService.pagingJoinCategoryAndEvalItem(page, size, nameCondition, categoryCondition);
        Long totalCount = deviceService.countByCondition(nameCondition, categoryCondition);

        return new PagingResultDto(deviceList, totalCount);
    }

    @GetMapping("/{id}")
    public DeviceDetailDto electronicDeviceDetail(@PathVariable(name = "id")Long id){
        DeviceDetailAndAvgDto findOneDetailDto = deviceService.findOneDetail(id);
        ElectronicDevice device = findOneDetailDto.getDevice();
        Map<Long, Double> avgGroupByEvalItemMap =
                findOneDetailDto.getAvgGroupByEvalItemMap();

        return new DeviceDetailDto(device, avgGroupByEvalItemMap);
    }

    @Getter
    @ToString
    public static class PagingResultDto {
        private final List<DevicePagingDto> deviceList;
        private final Long totalCount;

        public PagingResultDto(List<ElectronicDevice> deviceList, Long totalCount) {
            this.deviceList = deviceList
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
        private final String categoryName;
        private final Long imageId;
        private final String createdTime;
        private final List<String> evaluationItemList;

        public DevicePagingDto(ElectronicDevice device) {
            this.id = device.getId();
            this.name = device.getName();
            this.categoryName = device.getCategory().getName();
            this.imageId = device.getImage()
                    .map(Image::getId)
                    .orElse(null);
            this.createdTime = device.getCreatedTime()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM"));
            this.evaluationItemList = device.getEvaluationItemList()
                    .stream()
                    .map(EvaluationItem::getName)
                    .toList();
        }
    }
    @Getter
    @ToString
    public static class DeviceDetailDto{
        private final Long id;
        private final String name;
        private final String categoryName;
        private final Long imageId;
        private final String createdTime;
        private final List<EvalItemAvgDto> evalItemAvgList;

        public DeviceDetailDto(ElectronicDevice device, Map<Long, Double> avgGroupByEvalItemMap) {
            this.id = device.getId();
            this.name = device.getName();
            this.categoryName = device.getCategory().getName();
            this.imageId = device.getImage()
                    .map(Image::getId)
                    .orElse(null);
            this.createdTime = device.getCreatedTime()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            this.evalItemAvgList = device.getEvaluationItemList()
                    .stream()
                    .map((evalItem)-> new EvalItemAvgDto(
                            evalItem,
                            avgGroupByEvalItemMap.get(evalItem.getId())))
                    .toList();
        }
    }
    @Getter
    @ToString
    public static class EvalItemAvgDto{
        private final Long id;
        private final String name;
        private final Double avg;

        public EvalItemAvgDto(EvaluationItem evalItem, Double avg) {
            this.id = evalItem.getId();
            this.name = evalItem.getName();
            this.avg = avg;
        }
    }
}
