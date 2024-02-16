package backend.graduationprojectspring.service.impl;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.exception.HttpError;
import backend.graduationprojectspring.repository.CategoryRepo;
import backend.graduationprojectspring.repository.query.CategoryQueryRepo;
import backend.graduationprojectspring.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
            throw new HttpError("같은 이름으로 이미 존재하는 카테고리가 있습니다.", HttpStatus.CONFLICT);
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
                .orElseThrow(() -> new HttpError("해당하는 카테고리가 존재하지 않습니다.", HttpStatus.UNPROCESSABLE_ENTITY));
        if(categoryRepo.existsByName(updateName)){
            throw new HttpError("해당 이름으로 존재하는 카테고리가 이미 있습니다.", HttpStatus.CONFLICT);
        }
        findCategory.updateName(updateName);
    }

    @Override
    public void delete(Long id){
        categoryRepo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll(){
        return categoryRepo.findAllByOrderByName();
    }
}
