package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.entity.Image;
import backend.graduationprojectspring.repository.CategoryRepository;
import backend.graduationprojectspring.repository.ElectronicDeviceQueryRepository;
import backend.graduationprojectspring.repository.ElectronicDeviceRepository;
import backend.graduationprojectspring.repository.ImageRepository;
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
    private final ImageRepository imageRepository;

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
        return deviceQueryRepository.pagingFetchJoinCategoryAndDevice(page, size);
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
     * 전자제품 이미지 설정
     * @param deviceId 이미지를 설정할 전자제품 id
     * @param imageId 설정할 이미지의 id
     */
    public void setImage(Long deviceId, Long imageId){
        ElectronicDevice device = deviceRepository.findById(deviceId).orElseThrow();
        Image image = imageRepository.getReferenceById(imageId);
        device.setImage(image);
    }

}
