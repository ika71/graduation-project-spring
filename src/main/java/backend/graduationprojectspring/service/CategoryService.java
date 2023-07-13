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
     * 카테고리 페이지 조회(카테고리 이름으로 정렬됨)<br>
     * @param page 현재 보여줄 페이지 위치
     * @param size 얼마만큼 보여줄지 크기
     * @return 조회된 category List 반환
     */
    @Transactional(readOnly = true)
    public List<Category> paging(int page, int size){
        return categoryQueryRepository.paging(page, size);
    }

    /**
     * 카테고리 전체 개수 반환
     * @return 카테고리 전체 수
     */
    @Transactional(readOnly = true)
    public Long totalCount(){
        return categoryRepository.count();
    }

    /**
     * 카테고리 이름 수정
     * @param id 이름을 수정할 카테고리의 id
     * @param updateName 수정할 이름
     * @throws java.util.NoSuchElementException id에 해당하는 카테고리가 없으면 예외 반환
     */
    public void updateName(Long id, String updateName){
        Optional<Category> findCategoryOptional = categoryRepository.findById(id);
        Category findCategory = findCategoryOptional.orElseThrow();
        findCategory.updateName(updateName);
    }

    /**
     * 카테고리 삭제
     * @param id 삭제할 카테고리의 id
     * @throws IllegalArgumentException id에 해당하는 카테고리가 없으면 예외 반환
     */
    public void delete(Long id){
        categoryRepository.deleteById(id);
    }

    /**
     * 모든 카테고리 반환
     * @return 모든 카테고리 리스트 반환
     */
    @Transactional(readOnly = true)
    public List<Category> findAll(){
        return categoryRepository.findAllByOrderByName();
    }
}
