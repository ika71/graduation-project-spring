package backend.graduationprojectspring.repository;

import backend.graduationprojectspring.entity.Category;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static backend.graduationprojectspring.entity.QCategory.category;

@Repository
@RequiredArgsConstructor
public class CategoryQueryRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 카테고리 페이지 조회
     * @param page 현재 보여줄 페이지 위치
     * @param size 얼마만큼 보여줄지 크기
     * @return 조회된 CategoryList 반환
     */
    public List<Category> pagingCategory(int page, int size){
        return queryFactory
                .selectFrom(category)
                .orderBy(category.name.asc())
                .offset((long)(page - 1)*size)
                .limit(size)
                .fetch();
    }
}
