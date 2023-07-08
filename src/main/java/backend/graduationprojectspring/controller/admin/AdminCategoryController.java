package backend.graduationprojectspring.controller.admin;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.service.CategoryService;
import backend.graduationprojectspring.service.dto.CategoryServicePagingDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
        List<CategoryAllDto> categoryAllDtoList = categoryAllList
                .stream()
                .map(CategoryAllDto::new)
                .toList();
        return new categoryAllResultDto(categoryAllDtoList);
    }

    @GetMapping
    public categoryPagingResultDto categoryPaging(
            @RequestParam(name = "page", defaultValue = "1")int page,
            @RequestParam(name = "size", defaultValue = "10")int size
    ){
        CategoryServicePagingDto paging = categoryService.paging(page, size);
        List<Category> categoryList = paging.getCategoryList();
        Long categoryTotalCount = paging.getTotalCount();

        List<CategoryPagingDto> categoryPagingDtoList = categoryList
                .stream()
                .map(CategoryPagingDto::new)
                .toList();
        return new categoryPagingResultDto(categoryPagingDtoList, categoryTotalCount);
    }

    @PostMapping
    public ResponseEntity<?> categoryCreate(@RequestBody @Validated CategoryCreateDto categoryCreateDto){
        Category category = categoryCreateDto.toCategory();
        Category createdCategory = categoryService.create(category);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdCategory.getName());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> categoryUpdate(
            @PathVariable(name = "id")Long id,
            @RequestBody @Validated CategoryUpdateDto categoryUpdateDto){
        categoryService.update(id, categoryUpdateDto.toCategory());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> categoryDelete(@PathVariable (name = "id")Long id){
        categoryService.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Getter
    private static class categoryAllResultDto{
        List<CategoryAllDto> categoryAllDtoList;

        public categoryAllResultDto(List<CategoryAllDto> categoryAllDtoList) {
            this.categoryAllDtoList = categoryAllDtoList;
        }
    }

    @Getter
    private static class CategoryAllDto {
        private final Long id;
        private final String name;

        public CategoryAllDto(Category category) {
            this.id = category.getId();
            this.name = category.getName();
        }
    }

    @Getter
    private static class categoryPagingResultDto {
        List<CategoryPagingDto> categoryPagingDtoList;
        Long totalCount;

        public categoryPagingResultDto(List<CategoryPagingDto> categoryPagingDtoList, Long totalCount) {
            this.categoryPagingDtoList = categoryPagingDtoList;
            this.totalCount = totalCount;
        }
    }

    @Getter
    private static class CategoryPagingDto {
        private final Long id;
        private final String name;

        public CategoryPagingDto(Category category) {
            this.id = category.getId();
            this.name = category.getName();
        }
    }


    @Getter
    private static class CategoryCreateDto {
        @NotBlank
        private String name;

        public Category toCategory(){
            return new Category(name);
        }
    }

    @Getter
    private static class CategoryUpdateDto {
        @NotBlank
        private String name;

        public Category toCategory(){
            return new Category(name);
        }
    }
}
