package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CategoryServiceTest {
    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void create() {
        Category category = new Category("노트북");
        Category savedCategory = categoryService.create(category);
        Category findCategory = categoryRepository.findById(savedCategory.getId()).orElseThrow();

        assertThat(savedCategory.getId()).isEqualTo(findCategory.getId());
        assertThat(savedCategory.getName()).isEqualTo(findCategory.getName());
    }
}