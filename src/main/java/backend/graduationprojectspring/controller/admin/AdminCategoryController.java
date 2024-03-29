package backend.graduationprojectspring.controller.admin;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.service.CategoryService;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryService categoryService;

    @GetMapping("/all")
    public categoryAllResultDto categoryAll(){
        List<Category> categoryAllList = categoryService.findAll();
        return new categoryAllResultDto(categoryAllList);
    }

    @GetMapping
    public categoryPagingResultDto categoryPaging(
            @RequestParam(defaultValue = "1")int page,
            @RequestParam(defaultValue = "10")int size){
        List<Category> categoryPagingList = categoryService.paging(page, size);
        long categoryTotalCount = categoryService.totalCount();
        return new categoryPagingResultDto(categoryPagingList, categoryTotalCount);
    }

    @PostMapping
    public ResponseEntity<?> categoryCreate(@RequestBody @Validated CategoryCreateDto categoryCreateDto){
        Category createdCategory = categoryService.create(
                categoryCreateDto.getName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdCategory.getName());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> categoryUpdate(
            @PathVariable Long id,
            @RequestBody @Validated CategoryUpdateDto categoryUpdateDto){
        categoryService.updateName(id, categoryUpdateDto.getName());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> categoryDelete(@PathVariable Long id){
        categoryService.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Getter
    @ToString
    public static class categoryAllResultDto{
        List<CategoryAllDto> categoryList;

        public categoryAllResultDto(List<Category> categoryAllList) {
            this.categoryList = categoryAllList
                    .stream()
                    .map(CategoryAllDto::new)
                    .toList();
        }
    }

    @Getter
    @ToString
    public static class CategoryAllDto {
        private final Long id;
        private final String name;

        public CategoryAllDto(Category category) {
            this.id = category.getId();
            this.name = category.getName();
        }
    }

    @Getter
    @ToString
    public static class categoryPagingResultDto {
        private final List<CategoryPagingDto> categoryList;
        private final long totalCount;

        public categoryPagingResultDto(List<Category> categoryPagingList, Long totalCount) {
            this.categoryList = categoryPagingList
                    .stream()
                    .map(CategoryPagingDto::new)
                    .toList();
            this.totalCount = totalCount;
        }
    }

    @Getter
    @ToString
    public static class CategoryPagingDto {
        private final Long id;
        private final String name;

        public CategoryPagingDto(Category category) {
            this.id = category.getId();
            this.name = category.getName();
        }
    }


    @Getter
    @ToString
    public static class CategoryCreateDto {
        @NotBlank
        private String name;
    }

    @Getter
    @ToString
    public static class CategoryUpdateDto {
        @NotBlank
        private String name;
    }
}
