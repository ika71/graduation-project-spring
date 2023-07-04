package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.entity.Image;
import backend.graduationprojectspring.repository.ElectronicDeviceQueryRepository;
import backend.graduationprojectspring.repository.ElectronicDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ElectronicDeviceService {
    private final ElectronicDeviceRepository deviceRepository;
    private final ElectronicDeviceQueryRepository deviceQueryRepository;

    /**
     * 전자제품 데이터 베이스에 저장
     * @param electronicDevice 저장할 전자제품
     * @return 저장된 전자제품 반환
     */
    public ElectronicDevice create(ElectronicDevice electronicDevice){
        return deviceRepository.save(electronicDevice);
    }

    /**
     * 전자제품 페이지 조회<br>
     * 전자제품 이름으로 정렬됨<br>
     * 전자제품 카테고리 정보도 들어있음
     * @param page 현재 보여줄 페이지 위치
     * @param size 얼마만큼 보여줄지 크기
     * @return 조회된 ElectronicDevice List 반환
     */
    public List<ElectronicDevice> paging(int page, int size){
        return deviceQueryRepository.paging(page, size);
    }

    /**
     * 전체 카테고리 개수 반환
     * @return Long 타입 전체 카테고리 수
     */
    public Long totalCount(){
        return deviceRepository.count();
    }

    /**
     * 전자제품 수정
     * @param id 수정할 전자제품의 id
     * @param electronicDevice 수정할 내용을 가지고 있는 전자제품
     */
    public void update(Long id, ElectronicDevice electronicDevice){
        Optional<ElectronicDevice> findDeviceOptional = deviceRepository.findById(id);
        ElectronicDevice findDevice = findDeviceOptional.orElseThrow();
        findDevice.update(electronicDevice);
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
     * 순서는 바뀔 수 있음
     * @param deviceList
     * @return
     */
    public List<ElectronicDevice> fetchJoinEvaluationItem(List<ElectronicDevice> deviceList){
        return deviceQueryRepository.fetchJoinEvaluationItem(deviceList);
    }
}
