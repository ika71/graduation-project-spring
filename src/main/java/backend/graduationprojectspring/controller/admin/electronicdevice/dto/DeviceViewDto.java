package backend.graduationprojectspring.controller.admin.electronicdevice.dto;

import backend.graduationprojectspring.entity.ElectronicDevice;
import lombok.Getter;

@Getter
public class DeviceViewDto {
    private Long id;
    private String name;
    private CategoryDto categoryDto;

    public DeviceViewDto(ElectronicDevice device) {
        this.id = device.getId();
        this.name = device.getName();
        this.categoryDto = new CategoryDto(
                device.getCategory().getId(),
                device.getCategory().getName());
    }

    @Getter
    private static class CategoryDto{
        private Long id;
        private String name;

        public CategoryDto(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
