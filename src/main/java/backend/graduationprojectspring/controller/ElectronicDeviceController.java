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
import java.util.Optional;

@RestController
@RequestMapping("/device")
@RequiredArgsConstructor
public class ElectronicDeviceController {
    private final ElectronicDeviceService deviceService;
    @GetMapping
    public PagingResultDto electronicDevicePaging(
            @RequestParam(defaultValue = "1")int page,
            @RequestParam(defaultValue = "10") @Max(50) int size,
            @RequestParam(required = false) String nameCondition,
            @RequestParam(required = false) String categoryCondition){
        List<ElectronicDevice> deviceList = deviceService.pagingJoinCategoryAndEvalItem(page, size, nameCondition, categoryCondition);
        Long totalCount = deviceService.countByCondition(nameCondition, categoryCondition);
        List<Long> deviceIdList = deviceList
                .stream()
                .map(ElectronicDevice::getId)
                .toList();

        Map<Long, Double> totalAvgMap = deviceService.avgGroupByDevice(deviceIdList);
        Map<Long, Long> countBoardGroupByDeviceMap = deviceService.countBoardGroupByDevice(deviceIdList);

        return new PagingResultDto(deviceList, totalCount, totalAvgMap, countBoardGroupByDeviceMap);
    }

    @GetMapping("/{id}")
    public DeviceDetailDto electronicDeviceDetail(@PathVariable Long id){
        DeviceDetailAndAvgDto findOneDetailDto = deviceService.findOneDetail(id);
        ElectronicDevice device = findOneDetailDto.getDevice();
        Map<Long, Double> avgGroupByEvalItemMap =
                findOneDetailDto.getAvgGroupByEvalItemMap();

        Long relationDeviceCount = deviceService.countByCondition(null, device.getCategory().getName());

        return new DeviceDetailDto(device, avgGroupByEvalItemMap, relationDeviceCount);
    }

    @Getter
    @ToString
    public static class PagingResultDto {
        private final List<DevicePagingDto> deviceList;
        private final Long totalCount;

        public PagingResultDto(List<ElectronicDevice> deviceList, Long totalCount, Map<Long, Double> totalAvgMap, Map<Long, Long> countBoardGroupByDeviceMap) {
            this.deviceList = deviceList
                    .stream()
                    .map(device -> new DevicePagingDto(
                            device,
                            totalAvgMap.get(device.getId()),
                            countBoardGroupByDeviceMap.get(device.getId())))
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
        private final Optional<Double> totalAvg;
        private final Long boardCount;

        public DevicePagingDto(ElectronicDevice device, Double totalAvg, Long boardCount) {
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
            this.totalAvg = Optional.ofNullable(totalAvg);
            this.boardCount = boardCount;
        }

        public Double getTotalAvg() {
            if(totalAvg.isEmpty())return null;
            return Math.round(totalAvg.get()*10) / 10.0;
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
        private final Long relationDeviceCount;

        public DeviceDetailDto(ElectronicDevice device, Map<Long, Double> avgGroupByEvalItemMap, Long relationDeviceCount) {
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
            this.relationDeviceCount = relationDeviceCount;
        }
    }
    @Getter
    @ToString
    public static class EvalItemAvgDto{
        private final Long id;
        private final String name;
        private final Optional<Double> avg;

        public EvalItemAvgDto(EvaluationItem evalItem, Double avg) {
            this.id = evalItem.getId();
            this.name = evalItem.getName();
            this.avg = Optional.ofNullable(avg);
        }
        public Double getAvg() {
            if(avg.isEmpty())return null;
            return Math.round(avg.get()*10) / 10.0;
        }
    }
}
