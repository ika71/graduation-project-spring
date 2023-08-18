package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Member;
import backend.graduationprojectspring.exception.DuplicateException;

public interface MemberService {
    /**
     * 회원가입
     * @param member email이나 name이 데이터 베이스에 중복이 없어야 함
     * @return 저장된 member
     * @throws DuplicateException email이나 name이 데이터 베이스에 중복
     */
    Member create(Member member) throws DuplicateException;

    /**
     * email을 가지고 있는 아이디가 존재하고 비밀번호가 일치하면 토큰을 반환
     * @param email
     * @param password
     * @return JWT or null
     */
    String getToken(String email, String password);

    /**
     * id에 해당 하는 member를 반환
     * @param id
     * @return
     */
    Member findById(Long id);
}
