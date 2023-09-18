package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.exception.DuplicateException;
import backend.graduationprojectspring.exception.NotExistsException;
import backend.graduationprojectspring.service.dto.DeviceDetailAndAvgDto;

import java.util.List;

public interface ElectronicDeviceService {
    /**
     * 전자제품 데이터베이스에 저장
     * @param name 저장할 전자제품 이름
     * @param categoryId 저장할 전자제품이 속하는 카테고리 Id
     * @return 저장된 전자제품
     * @throws DuplicateException 같은 이름으로 존재하는 전자제품이 이미 있을 경우
     */
    ElectronicDevice create(String name, Long categoryId) throws DuplicateException;

    /**
     * 전자제품 페이지 조회<br>
     * 카테고리와 join<br>
     * @param page 현재 보여줄 페이지 위치
     * @param size 한 페이지의 크기
     * @return 조회된 ElectronicDevice List 반환
     */
    List<ElectronicDevice> pagingJoinCategory(int page, int size);

    /**
     * 전자제품 페이지 조회<br>
     * 카테고리와 join<br>
     * 평가항목과 left join
     * @param page 현재 보여줄 페이지 위치
     * @param size 한 페이지의 크기
     * @return 조회된 ElectronicDevice List 반환
     */
    List<ElectronicDevice> pagingJoinCategoryAndEvalItem(int page, int size);

    /**
     * 전자제품 전체 개수 반환
     * @return 전자제품 전체 수
     */
    long totalCount();

    /**
     * 전자제품 수정
     * @param deviceId 수정할 전자제품의 Id
     * @param updateDeviceName 수정할 이름
     * @param updateCategoryId 전자제품이 수정 후에 속할 카테고리 Id
     * @throws NotExistsException 전자제품이나 카테고리가 이미 없으면 발생
     * @throws DuplicateException 수정할 이름을 전자제품이 이미 존재하면 발생
     */
    void update(Long deviceId, String updateDeviceName, Long updateCategoryId) throws NotExistsException, DuplicateException;

    /**
     * 전자제품 삭제
     * @param id 삭제할 전자제품의 id
     */
    void delete(Long id);

    /**
     * 전자제품 이미지 설정
     * @param deviceId 이미지를 설정할 전자제품 id
     * @param imageId 설정할 이미지의 id
     * @throws NotExistsException 해당하는 전자제품이나 이미지가 없으면 발생
     */
    void setImage(Long deviceId, Long imageId) throws NotExistsException;

    /**
     * 전자제품 하나 조회<br>
     * 카테고리와 join<br>
     * 평가항목과 left join<br>
     * 전자제품이 가지고 있는 평가항목을 group by하여 평점 score의 평균을 계산함<br>
     * 계산 결과는 Map으로 반환 key = 평가항목 Id, value = score의 평균
     * @param id 조회할 전자제품 Id
     * @return 조회된 결과는 Dto로 반환
     * @throws NotExistsException 해당하는 전자제품이 없으면 발생
     */
    DeviceDetailAndAvgDto findOneDetail(Long id) throws NotExistsException;
}
