package backend.graduationprojectspring.controller.admin.category;

import backend.graduationprojectspring.controller.admin.category.dto.CategoryCreateDto;
import backend.graduationprojectspring.controller.admin.category.dto.CategoryUpdateDto;
import backend.graduationprojectspring.controller.admin.category.dto.CategoryViewDto;
import backend.graduationprojectspring.controller.admin.category.dto.CategoryViewListAndTotalCountDto;
import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public CategoryViewListAndTotalCountDto pagingCategory(
            @RequestParam(name = "page", defaultValue = "1")int page,
            @RequestParam(name = "size", defaultValue = "10")int size
    ){
        List<Category> categoryList = categoryService.paging(page, size);
        Long categoryTotalCount = categoryService.totalCount();

        List<CategoryViewDto> categoryViewDtoList = categoryList
                .stream()
                .map(CategoryViewDto::new)
                .toList();
        return new CategoryViewListAndTotalCountDto(categoryViewDtoList, categoryTotalCount);
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody @Validated CategoryCreateDto categoryCreateDto){
        Category category = categoryCreateDto.toCategory();
        Category createdCategory = categoryService.create(category);
        return ResponseEntity.ok().body(createdCategory.getName());
    }

    @PatchMapping
    public ResponseEntity<?> updateCategory(@RequestBody @Validated CategoryUpdateDto categoryUpdateDto){
        categoryService.update(categoryUpdateDto.getId(),
                categoryUpdateDto.toCategory());
        return ResponseEntity.ok().body(categoryUpdateDto.getName());
    }
}
