package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.repository.CategoryRepository;
import backend.graduationprojectspring.service.dto.CategoryServicePagingDto;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
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
    @Autowired
    EntityManager em;
    
    //저장된 데이터
    Category category1;
    Category category2;
    Category category3;
    @BeforeEach
    void beforeEach(){
        category1 = categoryService.create(new Category("노트북"));
        category2 = categoryService.create(new Category("컴퓨터"));
        category3 = categoryService.create(new Category("스마트폰"));
    }

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
        CategoryServicePagingDto paging1 = categoryService.paging(1, 2);
        List<Category> categoryList1 = paging1.getCategoryList();
        Long totalCount1 = paging1.getTotalCount();

        CategoryServicePagingDto paging2 = categoryService.paging(2, 2);
        List<Category> categoryList2 = paging2.getCategoryList();
        Long totalCount2 = paging2.getTotalCount();

        assertThat(categoryList1.get(0).getName()).isEqualTo("노트북");
        assertThat(categoryList1.get(1).getName()).isEqualTo("스마트폰");

        assertThat(categoryList2.get(0).getName()).isEqualTo("컴퓨터");
        assertThatThrownBy(()->categoryList2.get(1)).isInstanceOf(IndexOutOfBoundsException.class);

        assertThat(totalCount1).isEqualTo(3);
        assertThat(totalCount2).isEqualTo(3);
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
        categoryService.delete(category1.getId());

        assertThat(categoryRepository.count()).isEqualTo(2);
    }

    @Test
    void findAll(){
        List<Category> all = categoryService.findAll();
        assertThat(all.get(0).getName()).isEqualTo(category1.getName());
        assertThat(all.get(1).getName()).isEqualTo(category3.getName());
        assertThat(all.get(2).getName()).isEqualTo(category2.getName());
    }

    @Test
    void getReferenceById() {
        Category proxyCategory = categoryService.getReferenceById(category1.getId());
        boolean proxy = em.getEntityManagerFactory()
                .getPersistenceUnitUtil().isLoaded(proxyCategory);

        assertThat(proxy).isTrue();
    }
}