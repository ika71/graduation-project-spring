package backend.graduationprojectspring.service.dto;

import backend.graduationprojectspring.entity.Category;
import lombok.Getter;

import java.util.List;

@Getter
public class CategoryServicePagingDto {
    private final List<Category> categoryList;
    private final Long totalCount;

    public CategoryServicePagingDto(List<Category> categoryList, Long totalCount) {
        this.categoryList = categoryList;
        this.totalCount = totalCount;
    }
}
