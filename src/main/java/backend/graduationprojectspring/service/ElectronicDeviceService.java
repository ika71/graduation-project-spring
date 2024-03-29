package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.exception.HttpError;
import backend.graduationprojectspring.service.dto.DeviceDetailAndAvgDto;

import java.util.List;
import java.util.Map;

public interface ElectronicDeviceService {
    /**
     * 전자제품 데이터베이스에 저장
     * @param name 저장할 전자제품 이름
     * @param categoryId 저장할 전자제품이 속하는 카테고리 Id
     * @return 저장된 전자제품
     * @throws HttpError 같은 이름으로 존재하는 전자제품이 이미 있을 경우
     */
    ElectronicDevice create(String name, Long categoryId) throws HttpError;

    /**
     * 전자제품 페이지 조회(이름으로 정렬됨)<br>
     * 카테고리와 join<br>
     * 이미지와 left join 한다.
     * @param page 현재 보여줄 페이지 위치
     * @param size 한 페이지의 크기
     * @return 조회된 ElectronicDevice List 반환
     */
    List<ElectronicDevice> pagingJoinCategory(int page, int size);

    /**
     * 전자제품 페이지 조회(id 내림차순 정렬)<br>
     * 카테고리와 join<br>
     * 평가항목과 left join<br>
     * 이미지와 left join 한다.
     * @param page 현재 보여줄 페이지 위치
     * @param size 한 페이지의 크기
     * @param nameCondition 이름으로 검색하고 싶을 때 사용, null이면 검색조건 없음
     * @param categoryCondition 카테고리로 검색하고 싶을 때 사용, null이면 검색조건 없음
     * @return 조회된 ElectronicDevice List 반환
     */
    List<ElectronicDevice> pagingJoinCategoryAndEvalItem(int page, int size, String nameCondition, String categoryCondition);

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
     * @throws HttpError 전자제품이나 카테고리가 이미 없으면 발생
     * @throws HttpError 수정할 이름을 전자제품이 이미 존재하면 발생
     */
    void update(Long deviceId, String updateDeviceName, Long updateCategoryId) throws HttpError;

    /**
     * 전자제품 삭제
     * @param id 삭제할 전자제품의 id
     */
    void delete(Long id);

    /**
     * 전자제품 이미지 설정
     * @param deviceId 이미지를 설정할 전자제품 id
     * @param imageId 설정할 이미지의 id
     * @throws HttpError 해당하는 전자제품이나 이미지가 없으면 발생
     */
    void setImage(Long deviceId, Long imageId) throws HttpError;

    /**
     * 전자제품 하나 조회<br>
     * 카테고리와 join<br>
     * 평가항목과 left join<br>
     * 이미지와 left join<br>
     * 전자제품이 가지고 있는 평가항목을 group by하여 평점 score의 평균을 계산함<br>
     * 계산 결과는 Map으로 반환 key = 평가항목 Id, value = score의 평균
     * @param id 조회할 전자제품 Id
     * @return 조회된 결과는 Dto로 반환
     * @throws HttpError 해당하는 전자제품이 없으면 발생
     */
    DeviceDetailAndAvgDto findOneDetail(Long id) throws HttpError;

    /**
     * 검색 조건이 들어간 카운트 쿼리
     * @param nameCondition 이름으로 검색하고 싶을 때 사용, null이면 검색조건 없음
     * @param categoryCondition 카테고리로 검색하고 싶을 때 사용, null이면 검색조건 없음
     * @return 검색 조건에 걸린 ElectronicDevice 개수
     */
    Long countByCondition(String nameCondition, String categoryCondition);

    /**
     * 전자제품 별로 그룹화 하여 전자제품이 가지고 있는 모든 평점의 평균을 계산한다.
     * @param deviceIdList 검색할 device의 id를 모아둔 List
     * @return Map 자료구조로 반환한다.<br>
     * key = ElectronicDevice의 id
     * value = Evaluation.score 그룹 평균<br>
     * <b>Evaluation.score가 없다면 value는 null 된다.</b>
     */
    Map<Long, Double> avgGroupByDevice(List<Long> deviceIdList);

    /**
     * 전자제품 별로 그룹화 하여 게시글 개수를 가져옴
     * @param deviceIdList 검색할 device의 id를 모아둔 List
     * @return Map 자료구조로 반환한다.<br>
     * key = ElectronicDevice의 id
     * value = 전자제품이 가지고 있는 리뷰 글의 개수<br>
     */
    Map<Long, Long> countBoardGroupByDevice(List<Long> deviceIdList);
}
