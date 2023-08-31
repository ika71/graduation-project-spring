package backend.graduationprojectspring.service.dto;

import backend.graduationprojectspring.entity.ElectronicDevice;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

/**
 * 전자제품 정보와 전자제품이 가지고 있는 각 평가 요소들의 평점 평균을 나타내는 Dto다
 */
@Getter
@ToString
public class DeviceDetailAndAvgDto {
    private final ElectronicDevice device; //전자제품 정보
    private final Map<Long, Double> avgGroupByEvalItemMap; //전자제품 평가요소들의 평점 평균

    public DeviceDetailAndAvgDto(ElectronicDevice device, Map<Long, Double> avgGroupByEvalItemMap) {
        this.device = device;
        this.avgGroupByEvalItemMap = avgGroupByEvalItemMap;
    }
}
