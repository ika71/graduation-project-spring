package backend.graduationprojectspring.repository.query;

import backend.graduationprojectspring.entity.Category;

import java.util.List;

public interface CategoryQueryRepo {
    /**
     * 카테고리를 페이징 조회 한다.
     * @param page 조회할 페이지 위치
     * @param size 한 페이지의 크기
     * @return 조회된 Category 객체 리스트 반환
     */
    List<Category> paging(int page, int size);
}
