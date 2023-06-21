package backend.graduationprojectspring.controller.admin.category.dto;

import backend.graduationprojectspring.entity.Category;
import lombok.Getter;

@Getter
public class CategoryViewDto {
    private Long id;
    private String name;

    public CategoryViewDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
