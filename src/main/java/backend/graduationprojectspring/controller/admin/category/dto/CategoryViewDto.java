package backend.graduationprojectspring.controller.admin.category.dto;

import backend.graduationprojectspring.entity.Category;
import lombok.Getter;

@Getter
public class CategoryViewDto {
    private String name;

    public CategoryViewDto(Category category) {
        this.name = category.getName();
    }
}
