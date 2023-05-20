package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public Category create(Category category){
        return categoryRepository.save(category);
    }
}
