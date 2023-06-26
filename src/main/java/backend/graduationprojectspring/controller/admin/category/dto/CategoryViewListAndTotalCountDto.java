package backend.graduationprojectspring.controller.admin.category.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CategoryViewListAndTotalCountDto {
    List<CategoryViewDto> categoryViewDtoList;
    Long totalCount;

    public CategoryViewListAndTotalCountDto(List<CategoryViewDto> categoryViewDtoList, Long totalCount) {
        this.categoryViewDtoList = categoryViewDtoList;
        this.totalCount = totalCount;
    }
}
