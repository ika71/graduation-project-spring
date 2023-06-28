package backend.graduationprojectspring.controller.admin.evaluation_item.dto;

import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.entity.EvaluationItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class EvaluationItemCreateDto {
    @NotNull
    private Long electronicDeviceId;
    @NotBlank
    private String name;

    public EvaluationItem toEvaluationItem(ElectronicDevice electronicDevice){
        return new EvaluationItem(name, electronicDevice);
    }
}
