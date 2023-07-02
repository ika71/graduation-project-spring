package backend.graduationprojectspring.controller.admin.electronic_device.dto;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.entity.ElectronicDevice;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DeviceUpdateDto {
    @NotBlank
    private String name;
    @NotNull
    private Long categoryId;

    public ElectronicDevice toElectronicDevice(Category category){
        return new ElectronicDevice(name, category);
    }
}
