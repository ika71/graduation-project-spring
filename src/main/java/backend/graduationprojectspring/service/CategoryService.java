package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.exception.DuplicateException;
import backend.graduationprojectspring.exception.NotExistsException;

import java.util.List;

public interface CategoryService {
    /**
     * 카테고리를 생성한다.
     * @param name 생성할 카테고리 이름
     * @return 생성된 카테고리 반환
     * @throws DuplicateException 같은 이름으로 존재하는 카테고리가 있을 경우 발생
     */
    Category create(String name);

    /**
     * 카테고리 페이지 조회<br>
     * @param page 현재 보여줄 페이지 위치
     * @param size 한 페이지의 크기
     * @return 조회된 category List 반환
     */
    List<Category> paging(int page, int size);

    /**
     * 카테고리 전체 개수 반환
     * @return 카테고리 전체 수
     */
    Long totalCount();

    /**
     * 카테고리 이름 수정
     * @param id 이름을 수정할 카테고리의 id
     * @param updateName 수정할 이름
     * @throws NotExistsException id에 해당하는 카테고리가 없으면 예외 발생
     * @throws DuplicateException 수정할 이름으로 존재하는 카테고리가 이미 존재하면 발생
     */
    void updateName(Long id, String updateName);

    /**
     * 카테고리 삭제
     * @param id 삭제할 카테고리의 id
     * @throws IllegalArgumentException id가 null이 들어올 경우 예외 반환
     */
    void delete(Long id);

    /**
     * 모든 카테고리 반환
     * @return 모든 카테고리 리스트 반환
     */
    List<Category> findAll();
}
