package backend.graduationprojectspring.controller.admin.electronic_device.dto;

import backend.graduationprojectspring.entity.ElectronicDevice;
import lombok.Getter;

@Getter
public class DevicePagingDto {
    private final Long id;
    private final String name;
    private final CategoryDto categoryDto;
    private final Long imageId;

    public DevicePagingDto(ElectronicDevice device) {
        this.id = device.getId();
        this.name = device.getName();
        this.categoryDto = new CategoryDto(
                device.getCategory().getId(),
                device.getCategory().getName());
        if(device.getImage() != null){
            this.imageId = device.getImage().getId();
        }
        else{
            this.imageId = null;
        }
    }

    @Getter
    private static class CategoryDto{
        private final Long id;
        private final String name;

        public CategoryDto(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
