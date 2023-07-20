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
     * 카테고리를 페이징 조회 한다.<br>
     * 카테고리 이름으로 정렬되어 있다.
     * @param page 현재 보여줄 페이지 위치
     * @param size 한 페이지의 사이즈
     * @return 조회된 CategoryList 반환
     */
    public List<Category> paging(int page, int size){
        return queryFactory
                .selectFrom(category)
                .orderBy(category.name.asc())
                .offset((long)(page - 1)*size)
                .limit(size)
                .fetch();
    }
}
