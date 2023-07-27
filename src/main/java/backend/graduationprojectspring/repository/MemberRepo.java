package backend.graduationprojectspring.repository;


import backend.graduationprojectspring.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepo extends JpaRepository<Member, Long> {
    /**
     * Email을 조건으로 사용해 Member를 찾는다
     * @param email 조건에 사용될 email 문자열
     * @return 조회된 Member를 반환한다.
     */
    Member findByEmail(String email);

    /**
     * email 또는 name으로 이미 생성된 member가 있는지 확인한다.
     * @param email 검색 조건으로 쓰일 email
     * @param name  검색 조건으로 쓰일 name
     * @return 이미 생성된 member가 있으면 true 없으면 false를 반환한다.
     */
    Boolean existsByEmailOrName(String email, String name);
}
