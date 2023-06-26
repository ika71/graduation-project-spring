package backend.graduationprojectspring.controller.admin.electronicdevice.dto;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.entity.ElectronicDevice;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DeviceCreateDto {
    @NotNull
    private Long categoryId;
    @NotEmpty
    private String name;

    public ElectronicDevice toElectronicDevice(){
        Category category = new Category(categoryId);
        return new ElectronicDevice(name, category);
    }
}
