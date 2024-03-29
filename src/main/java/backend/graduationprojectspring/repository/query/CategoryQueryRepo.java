package backend.graduationprojectspring.repository.query;

import backend.graduationprojectspring.entity.Category;

import java.util.List;

public interface CategoryQueryRepo {
    /**
     * Category를 페이징 조회 한다.(카테고리 이름으로 정렬됨)<br>
     * @param page 현재 보여줄 페이지 위치
     * @param size 한 페이지의 사이즈
     * @return 조회된 CategoryList 반환
     */
    List<Category> paging(int page, int size);
}
