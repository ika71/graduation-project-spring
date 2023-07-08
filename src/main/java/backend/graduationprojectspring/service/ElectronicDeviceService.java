package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.entity.Image;
import backend.graduationprojectspring.repository.CategoryRepository;
import backend.graduationprojectspring.repository.ElectronicDeviceQueryRepository;
import backend.graduationprojectspring.repository.ElectronicDeviceRepository;
import backend.graduationprojectspring.service.dto.ElectronicDeviceServicePagingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ElectronicDeviceService {
    private final ElectronicDeviceRepository deviceRepository;
    private final ElectronicDeviceQueryRepository deviceQueryRepository;
    private final CategoryRepository categoryRepository;

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
     * 전자제품 totalCount 조회
     * @param page 현재 보여줄 페이지 위치
     * @param size 얼마만큼 보여줄지 크기
     * @return 조회된 ElectronicDevice List, totalCount 반환
     */
    public ElectronicDeviceServicePagingDto paging(int page, int size){
        List<ElectronicDevice> pagingDeviceList = deviceQueryRepository.pagingFetchJoinCategory(page, size);
        Long totalCount = deviceRepository.count();

        return new ElectronicDeviceServicePagingDto(pagingDeviceList, totalCount);
    }

    /**
     * 전자제품 수정
     * @param deviceId 수정할 전자제품의 Id
     * @param updateDevice 수정한 내용을 담은 전자제품 객체
     * @param updateCategoryId 전자제품이 수정 후에 속할 카테고리 Id
     */
    public void update(Long deviceId, ElectronicDevice updateDevice, Long updateCategoryId){
        ElectronicDevice findDevice = deviceRepository.findById(deviceId).orElseThrow();
        Category updateCategory = categoryRepository.getReferenceById(updateCategoryId);

        findDevice.update(updateDevice);
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
     * Id 값을 가지고 있는 전자제품 프록시 객체 반환
     * @param id 프록시 객체가 가지고 있을 id 값
     * @return ElectronicDevice 프록시 객체 반환
     */
    public ElectronicDevice  getReferenceById(Long id){
        return deviceRepository.getReferenceById(id);
    }

    /**
     * 전자제품의 이미지를 설정
     * @param id 이미지를 설정할 전자제품의 id
     * @param image 전자제품에 설정할 이미지
     */
    public void setImage(Long id, Image image){
        ElectronicDevice device = deviceRepository.findById(id).orElseThrow();
        device.setImage(image);
    }

    /**
     * deviceList에 있는 device들의 evaluationList를 fetchjoin함<br>
     * evaluationList의 크기가 0이어서 조인 대상이 되지 않은
     * device들은 원본 상태 그대로 놔둠(left join)<br>
     * 이름 순 정렬
     * @param deviceList
     * @return evaluationList를 fetch join한 ElectronicDevice 리스트 반환
     */
    public List<ElectronicDevice> fetchJoinEvaluationItem(List<ElectronicDevice> deviceList){
        return deviceQueryRepository.fetchJoinEvaluationItem(deviceList);
    }
}
