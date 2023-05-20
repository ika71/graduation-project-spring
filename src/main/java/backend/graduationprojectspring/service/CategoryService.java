package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    /**
     * category 데이터 베이스에 저장
     * @param category
     * @return
     */
    @Transactional
    public Category create(Category category){
        return categoryRepository.save(category);
    }

    /**
     * categoryId로 찾아서 Optional로 반환
     * @param categoryId
     * @return
     */
    @Transactional
    public Optional<Category> findById(Long categoryId){
        return categoryRepository.findById(categoryId);
    }
}
