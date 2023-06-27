package backend.graduationprojectspring.controller.admin.electronicdevice.dto;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.entity.ElectronicDevice;
import lombok.Getter;

@Getter
public class DeviceUpdateDto {
    private Long id;
    private String name;
    private Long categoryId;

    public ElectronicDevice toElectronicDevice(){
        return new ElectronicDevice(name, new Category(categoryId));
    }
}
