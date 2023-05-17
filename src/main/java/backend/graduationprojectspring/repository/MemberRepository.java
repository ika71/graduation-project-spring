package backend.graduationprojectspring.repository;


import backend.graduationprojectspring.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Boolean existsByEmailOrName(String email, String name);
}
