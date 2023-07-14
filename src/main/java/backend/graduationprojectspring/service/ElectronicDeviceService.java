package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.entity.EvaluationItem;
import backend.graduationprojectspring.entity.Image;
import backend.graduationprojectspring.repository.*;
import backend.graduationprojectspring.service.dto.DeviceDetailAndAvgDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class ElectronicDeviceService {
    private final ElectronicDeviceRepository deviceRepository;
    private final ElectronicDeviceQueryRepository deviceQueryRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final EvaluationQueryRepository evalQueryRepository;

    /**
     * 전자제품 데이터베이스에 저장
     * @param electronicDevice 저장할 전자제품
     * @param categoryId 저장할 전자제품이 속하는 카테고리 Id
     * @return 저장된 전자제품
     */
    public ElectronicDevice create(ElectronicDevice electronicDevice, Long categoryId){
        Category category = categoryRepository.getReferenceById(categoryId);
        electronicDevice.setCategory(category);

        return deviceRepository.save(electronicDevice);
    }

    /**
     * 전자제품 페이지 조회(전자제품 카테고리 정보 fetch join)<br>
     * 전자제품 이름으로 정렬됨<br>
     * @param page 현재 보여줄 페이지 위치
     * @param size 얼마만큼 보여줄지 크기
     * @return 조회된 ElectronicDevice List 반환
     */
    @Transactional(readOnly = true)
    public List<ElectronicDevice> pagingJoinCategory(int page, int size){
        return deviceQueryRepository.pagingFetchJoinCategory(page, size);
    }

    /**
     * 전자제품 페이지 조회
     * (전자제품 카테고리 fetch join, 평가항목 fetch left join)<br>
     * 전자제품 이름으로 정렬됨<br>
     * @param page 현재 보여줄 페이지 위치
     * @param size 얼마만큼 보여줄지 크기
     * @return 조회된 ElectronicDevice List 반환
     */
    @Transactional(readOnly = true)
    public List<ElectronicDevice> pagingJoinCategoryAndDevice(int page, int size){
        return deviceQueryRepository.pagingFetchJoinCategoryAndEvalItem(page, size);
    }

    /**
     * 전자제품 전체 개수 반환
     * @return 전자제품 전체 수
     */
    @Transactional(readOnly = true)
    public Long totalCount(){
        return deviceRepository.count();
    }

    /**
     * 전자제품 수정
     * @param deviceId 수정할 전자제품의 Id
     * @param updateDeviceName 수정할 이름
     * @param updateCategoryId 전자제품이 수정 후에 속할 카테고리 Id
     */
    public void update(Long deviceId, String updateDeviceName, Long updateCategoryId){
        ElectronicDevice findDevice = deviceRepository.findById(deviceId).orElseThrow();
        Category updateCategory = categoryRepository.getReferenceById(updateCategoryId);

        findDevice.updateName(updateDeviceName);
        findDevice.setCategory(updateCategory);
    }

    /**
     * 전자제품 삭제
     * @param id 삭제할 전자제품의 id
     * @throws IllegalArgumentException id에 해당하는 카테고리가 없으면 예외 반환
     */
    public void delete(Long id){
        deviceRepository.deleteById(id);
    }

    /**
     * 전자제품 이미지 설정
     * @param deviceId 이미지를 설정할 전자제품 id
     * @param imageId 설정할 이미지의 id
     */
    public void setImage(Long deviceId, Long imageId){
        ElectronicDevice device = deviceRepository.findById(deviceId).orElseThrow();
        Image image = imageRepository.getReferenceById(imageId);
        device.setImage(image);
    }

    /**
     * 전자제품 하나 조회<br>
     * 해당 전자제품은 카테고리, 평가항목을 fetch join 함<br>
     * 전자제품이 가지고 있는 평가항목을 group by하여 평점 score의 평균을 계산함<br>
     * 계산 결과는 Map으로 반환 key = 평가항목 Id, value = score의 평균
     * @param id 조회할 전자제품 Id
     * @return 조회된 결과는 Dto로 반환
     */
    @Transactional(readOnly = true)
    public DeviceDetailAndAvgDto findOneDetail(Long id){
        ElectronicDevice device = deviceQueryRepository
                .findOneFetchJoinCategoryAndEvalItem(id);

        List<Long> evalItemIdList = device.getEvaluationItemList()
                .stream()
                .map(EvaluationItem::getId)
                .toList();

        Map<Long, Double> avgGroupByEvalItemMap = evalQueryRepository.avgGroupByEvalItem(evalItemIdList);

        return new DeviceDetailAndAvgDto(device, avgGroupByEvalItemMap);
    }
}
