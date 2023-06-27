package backend.graduationprojectspring.controller.admin.electronicdevice.dto;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.entity.ElectronicDevice;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DeviceUpdateDto {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private Long categoryId;

    public ElectronicDevice toElectronicDevice(){
        return new ElectronicDevice(name, new Category(categoryId));
    }
}
