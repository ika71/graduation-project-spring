package backend.graduationprojectspring.controller.admin.electronic_device.dto;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.entity.ElectronicDevice;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DeviceCreateDto {
    @NotNull
    private Long categoryId;
    @NotBlank
    private String name;

    public ElectronicDevice toElectronicDevice(Category category){
        return new ElectronicDevice(name, category);
    }
}
