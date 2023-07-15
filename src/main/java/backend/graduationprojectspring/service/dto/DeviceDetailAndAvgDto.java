package backend.graduationprojectspring.service.dto;

import backend.graduationprojectspring.entity.ElectronicDevice;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
public class DeviceDetailAndAvgDto {
    private final ElectronicDevice device;
    private final Map<Long, Double> avgGroupByEvalItemMap;

    public DeviceDetailAndAvgDto(ElectronicDevice device, Map<Long, Double> avgGroupByEvalItemMap) {
        this.device = device;
        this.avgGroupByEvalItemMap = avgGroupByEvalItemMap;
    }
}
