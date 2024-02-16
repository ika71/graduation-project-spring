package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Member;
import backend.graduationprojectspring.exception.HttpError;
import backend.graduationprojectspring.service.dto.SigninDto;

import java.util.Optional;

public interface MemberService {
    /**
     * 회원가입
     * @param member email이나 name이 데이터 베이스에 중복이 없어야 함
     * @return 저장된 member
     * @throws HttpError email이나 name이 데이터 베이스에 중복
     */
    Member create(Member member) throws HttpError;

    /**
     * email을 가지고 있는 아이디가 존재하고 비밀번호가 일치하면 토큰을 반환
     * @param email
     * @param password
     * @return JWT or null
     */
    Optional<SigninDto> signin(String email, String password);

    /**
     * id에 해당 하는 member를 반환
     * @param id member의 id
     * @return 찾아온 member 객체
     * @throws HttpError id에 해당하는 member가 없으면 반환
     */
    Member findById(Long id) throws HttpError;

    /**
     * 유저의 권한을 확인할 수 있는 jwt 토큰을 발급한다.
     * @param id member의 id
     * @param role member의 Role
     * @return jwt 토큰
     */

    String createAccessToken(String id, String role);
}
