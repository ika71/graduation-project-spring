package backend.graduationprojectspring.controller.admin.category.dto;

import backend.graduationprojectspring.entity.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CategoryUpdateDto {
    @NotNull
    private Long id;

    @NotEmpty
    private String name;

    public Category toCategory(){
        return new Category(name);
    }
}
