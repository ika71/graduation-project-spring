package backend.graduationprojectspring.controller.admin.electronic_device.dto;

import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.entity.Image;
import lombok.Getter;

import java.util.Optional;

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
        this.imageId = Optional.ofNullable(device.getImage())
                .map(Image::getId)
                .orElse(null);
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
