package backend.graduationprojectspring.controller.admin.category;

import backend.graduationprojectspring.controller.admin.category.dto.CreateCategoryDto;
import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryService categoryService;

    @PostMapping("/category")
    public ResponseEntity<?> createCategory(@RequestBody @Validated CreateCategoryDto createCategoryDto){
        Category category = createCategoryDto.toCategory();
        Category createdCategory = categoryService.create(category);
        return ResponseEntity.ok().body(createdCategory.getName());
    }
}
