package backend.graduationprojectspring.controller.admin.dto;

import backend.graduationprojectspring.entity.Category;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class CreateCategoryDto {
    @NotEmpty
    private String name;

    public Category toCategory(){
        return new Category(name);
    }
}
