package backend.graduationprojectspring.controller.admin.electronic_device.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DeviceImageSetDto {
    @NotNull
    private Long imageId;
}
