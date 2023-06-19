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
     * @param category
     * @return
     */
    public Category create(Category category){
        return categoryRepository.save(category);
    }

    /**
     * categoryId로 찾아서 Optional로 반환
     * @param categoryId
     * @return
     */
    public Optional<Category> findById(Long categoryId){
        return categoryRepository.findById(categoryId);
    }
    public List<Category> pagingCategory(int page, int size){
        return categoryQueryRepository.pagingCategory(page, size);
    }
}
