package backend.graduationprojectspring.repository.query.impl;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.repository.query.CategoryQueryRepo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static backend.graduationprojectspring.entity.QCategory.category;

@Repository
@RequiredArgsConstructor
public class CategoryQueryRepoImpl implements CategoryQueryRepo {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Category> paging(int page, int size){
        return queryFactory
                .selectFrom(category)
                .orderBy(category.name.asc())
                .offset((long)(page - 1)*size)
                .limit(size)
                .fetch();
    }
}
