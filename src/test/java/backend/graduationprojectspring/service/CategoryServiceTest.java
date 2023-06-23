package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
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
    @Test
    void pagingCategory(){
        Category category1 = new Category("노트북");
        Category category2 = new Category("컴퓨터");
        Category category3 = new Category("스마트폰");
        categoryService.create(category1);
        categoryService.create(category2);
        categoryService.create(category3);

        List<Category> categories1 = categoryService.paging(1, 2);
        assertThat(categories1.get(0).getName()).isEqualTo("노트북");
        assertThat(categories1.get(1).getName()).isEqualTo("스마트폰");

        List<Category> categories2 = categoryService.paging(2, 2);
        assertThat(categories2.get(0).getName()).isEqualTo("컴퓨터");
        assertThatThrownBy(()->categories2.get(1)).isInstanceOf(IndexOutOfBoundsException.class);
    }
    @Test
    void total(){
        assertThat(categoryService.totalCount()).isEqualTo(0);

        Category category1 = new Category("노트북");
        Category category2 = new Category("컴퓨터");
        Category category3 = new Category("스마트폰");
        categoryService.create(category1);
        categoryService.create(category2);
        categoryService.create(category3);

        assertThat(categoryService.totalCount()).isEqualTo(3);
    }

    @Test
    void update(){
        Category category = new Category("노트북");
        Category createdCategory = categoryService.create(category);

        Category updateCategory = new Category("스마트폰");
        categoryService.update(createdCategory.getId(), updateCategory);
        Category findCategory = categoryRepository.findById(createdCategory.getId()).orElseThrow();
        assertThat(findCategory.getName()).isEqualTo(updateCategory.getName());
    }
    @Test
    void delete(){
        Category category = new Category("노트북");
        Category createdCategory = categoryService.create(category);

        categoryService.delete(createdCategory.getId());

        assertThat(categoryService.totalCount()).isEqualTo(0);
    }
}