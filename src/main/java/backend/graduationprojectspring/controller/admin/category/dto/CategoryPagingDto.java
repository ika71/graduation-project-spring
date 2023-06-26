package backend.graduationprojectspring.controller.admin.category.dto;

import backend.graduationprojectspring.entity.Category;
import lombok.Getter;

@Getter
public class CategoryPagingDto {
    private final Long id;
    private final String name;

    public CategoryPagingDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}