package backend.graduationprojectspring.controller.admin.category.dto;

import backend.graduationprojectspring.entity.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CategoryUpdateDto {
    @NotBlank
    private String name;

    public Category toCategory(){
        return new Category(name);
    }
}
