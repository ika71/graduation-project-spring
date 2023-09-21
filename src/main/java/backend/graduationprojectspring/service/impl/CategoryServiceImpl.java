package backend.graduationprojectspring.service.impl;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.exception.DuplicateException;
import backend.graduationprojectspring.exception.NotExistsException;
import backend.graduationprojectspring.repository.CategoryRepo;
import backend.graduationprojectspring.repository.query.CategoryQueryRepo;
import backend.graduationprojectspring.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;
    private final CategoryQueryRepo categoryQueryRepo;

    @Override
    public Category create(String name){
        if(categoryRepo.existsByName(name)){
            throw new DuplicateException("같은 이름으로 이미 존재하는 카테고리가 있습니다.");
        }
        Category category = new Category(name);
        return categoryRepo.save(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> paging(int page, int size){
        return categoryQueryRepo.paging(page, size);
    }

    @Override
    @Transactional(readOnly = true)
    public long totalCount(){
        return categoryRepo.count();
    }

    @Override
    public void updateName(Long id, String updateName){
        Category findCategory = categoryRepo.findById(id)
                .orElseThrow(() -> new NotExistsException("해당하는 카테고리가 존재하지 않습니다."));
        if(categoryRepo.existsByName(updateName)){
            throw new DuplicateException("해당 이름으로 존재하는 카테고리가 이미 있습니다.");
        }
        findCategory.updateName(updateName);
    }

    @Override
    public void delete(Long id){
        if(id == null){
            throw new IllegalArgumentException("id가 null이 올 수 없습니다.");
        }
        categoryRepo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll(){
        return categoryRepo.findAllByOrderByName();
    }
}
