package backend.graduationprojectspring.controller.admin.category;

import backend.graduationprojectspring.controller.admin.category.dto.CategoryAllDto;
import backend.graduationprojectspring.controller.admin.category.dto.CategoryCreateDto;
import backend.graduationprojectspring.controller.admin.category.dto.CategoryPagingDto;
import backend.graduationprojectspring.controller.admin.category.dto.CategoryUpdateDto;
import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.service.CategoryService;
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
        List<Category> categoryList = categoryService.paging(page, size);
        Long categoryTotalCount = categoryService.totalCount();

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

    @PatchMapping
    public ResponseEntity<?> categoryUpdate(@RequestBody @Validated CategoryUpdateDto categoryUpdateDto){
        categoryService.update(categoryUpdateDto.getId(),
                categoryUpdateDto.toCategory());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping
    public ResponseEntity<?> categoryDelete(@RequestParam (name = "id")Long id){
        categoryService.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
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
    private static class categoryAllResultDto{
        List<CategoryAllDto> categoryAllDtoList;

        public categoryAllResultDto(List<CategoryAllDto> categoryAllDtoList) {
            this.categoryAllDtoList = categoryAllDtoList;
        }
    }
}
