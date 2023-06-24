package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.ElectronicDevice;
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
     * 전자제품 카테고리 정보도 들어있음
     * @param page 현재 보여줄 페이지 위치
     * @param size 얼마만큼 보여줄지 크기
     * @return 조회된 List<ElectronicDevice> 반환
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
}
