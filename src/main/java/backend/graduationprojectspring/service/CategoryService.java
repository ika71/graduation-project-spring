package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.repository.CategoryQueryRepository;
import backend.graduationprojectspring.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryQueryRepository categoryQueryRepository;

    /**
     * category 데이터 베이스에 저장
     * @param category 저장할 카테고리
     * @return 저장된 카테고리 반환
     */
    public Category create(Category category){
        return categoryRepository.save(category);
    }

    /**
     * 카테고리 페이지 조회
     * @param page 현재 보여줄 페이지 위치
     * @param size 얼마만큼 보여줄지 크기
     * @return 조회된 List<Category> 반환
     */
    public List<Category> paging(int page, int size){
        return categoryQueryRepository.paging(page, size);
    }

    /**
     * 전체 카테고리 개수 반환
     * @return Long 타입 전체 카테고리 수
     */
    public Long totalCount(){
        return categoryRepository.count();
    }

    /**
     * 카테고리 수정
     * @param id 수정할 카테고리의 id
     * @param category 수정할 내용을 가지고 있는 카테고리
     * @throws java.util.NoSuchElementException id에 해당하는 카테고리가 없으면 예외 반환
     */
    public void update(Long id, Category category){
        Optional<Category> findCategoryOptional = categoryRepository.findById(id);
        Category findCategory = findCategoryOptional.orElseThrow();
        findCategory.update(category);
    }

    /**
     * 카테고리 삭제
     * @param id 삭제할 카테고리의 id
     * @throws IllegalArgumentException id에 해당하는 카테고리가 없으면 예외 반환
     */
    public void delete(Long id){
        categoryRepository.deleteById(id);
    }
}
