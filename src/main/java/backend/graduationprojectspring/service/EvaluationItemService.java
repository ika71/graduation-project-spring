package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.EvaluationItem;
import backend.graduationprojectspring.exception.DuplicateException;
import backend.graduationprojectspring.exception.NotExistsException;

import java.util.List;

public interface EvaluationItemService {
    /**
     * 평가항목 데이터베이스에 저장
     * @param name 저장할 평가항목 이름
     * @param deviceId 평가항목이 속하는 전자제품 id
     * @return 저장된 평가항목
     * @throws DuplicateException 전자제품에 같은 이름으로 평가항목이 이미 있을 경우
     */
    EvaluationItem create(String name, Long deviceId) throws DuplicateException;

    /**
     * 전자제품 Id를 외래키로 갖는 모든 평가 항목 반환<br>
     * @param electronicDeviceId 전자제품 Id
     * @return 전자제품 Id를 외래키로 갖는 모든 평가 항목 반환
     */
    List<EvaluationItem> findAllByElectronicDeviceId(Long electronicDeviceId);

    /**
     * 평가항목 이름을 변경
     * @param id 변경할 평가항목의 id
     * @param name 변경할 이름
     * @throws NotExistsException 해당하는 평가항목이 없으면 발생
     * @throws DuplicateException 이미 존재하는 이름의 평가항목으로 수정하려 할 때 발생
     */
    void updateName(Long id, String name) throws NotExistsException, DuplicateException;

    /**
     * 평가항목 삭제
     * @param id 삭제할 평가항목의 id
     */
    void delete(Long id);
}
