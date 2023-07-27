package backend.graduationprojectspring.service.impl;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.exception.DuplicateException;
import backend.graduationprojectspring.exception.NotExistsException;
import backend.graduationprojectspring.repository.CategoryRepo;
import backend.graduationprojectspring.repository.query.CategoryQueryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepo categoryRepo;
    private final CategoryQueryRepo categoryQueryRepo;

    /**
     * 카테고리 데이터 베이스에 저장
     * @param name 저장할 카테고리 이름
     * @return 저장된 카테고리 반환
     * @throws DuplicateException 같은 이름으로 존재하는 카테고리가 있을 경우
     */
    public Category create(String name){
        if(categoryRepo.existsByName(name)){
            throw new DuplicateException("같은 이름으로 이미 존재하는 카테고리가 있습니다.");
        }
        Category category = new Category(name);
        return categoryRepo.save(category);
    }

    /**
     * 카테고리 페이지 조회(카테고리 이름으로 정렬됨)<br>
     * @param page 현재 보여줄 페이지 위치
     * @param size 얼마만큼 보여줄지 크기
     * @return 조회된 category List 반환
     */
    @Transactional(readOnly = true)
    public List<Category> paging(int page, int size){
        return categoryQueryRepo.paging(page, size);
    }

    /**
     * 카테고리 전체 개수 반환
     * @return 카테고리 전체 수
     */
    @Transactional(readOnly = true)
    public Long totalCount(){
        return categoryRepo.count();
    }

    /**
     * 카테고리 이름 수정
     * @param id 이름을 수정할 카테고리의 id
     * @param updateName 수정할 이름
     * @throws NotExistsException id에 해당하는 카테고리가 없으면 예외 발생
     * @throws DuplicateException 수정할 이름으로 존재하는 카테고리가 이미 존재하면 발생
     */
    public void updateName(Long id, String updateName){
        Category findCategory = categoryRepo.findById(id)
                .orElseThrow(() -> new NotExistsException("해당하는 카테고리가 존재하지 않습니다."));
        if(categoryRepo.existsByName(updateName)){
            throw new DuplicateException("해당 이름으로 존재하는 카테고리가 이미 있습니다.");
        }
        findCategory.updateName(updateName);
    }

    /**
     * 카테고리 삭제
     * @param id 삭제할 카테고리의 id
     * @throws IllegalArgumentException id가 null이 들어올 경우 예외 반환
     */
    public void delete(Long id){
        categoryRepo.deleteById(id);
    }

    /**
     * 모든 카테고리 반환
     * @return 모든 카테고리 리스트 반환
     */
    @Transactional(readOnly = true)
    public List<Category> findAll(){
        return categoryRepo.findAllByOrderByName();
    }
}
