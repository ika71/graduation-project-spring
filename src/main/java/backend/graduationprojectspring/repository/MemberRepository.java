package backend.graduationprojectspring.repository;


import backend.graduationprojectspring.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);
    Boolean existsByEmailOrName(String email, String name);
}
