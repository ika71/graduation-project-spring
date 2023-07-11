package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.entity.EvaluationItem;
import backend.graduationprojectspring.repository.ElectronicDeviceRepository;
import backend.graduationprojectspring.repository.EvaluationItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EvaluationItemService {
    private final EvaluationItemRepository itemRepository;
    private final ElectronicDeviceRepository deviceRepository;

    /**
     * 평가항목 데이터베이스에 저장
     * @param evaluationItem 저장할 평가항목
     * @param deviceId 평가항목이 속하는 전자제품 id
     * @return 저장된 평가항목
     */
    public EvaluationItem create(EvaluationItem evaluationItem, Long deviceId){
        ElectronicDevice device = deviceRepository.getReferenceById(deviceId);
        evaluationItem.setElectronicDevice(device);
        return itemRepository.save(evaluationItem);
    }

    /**
     * 전자제품 Id를 외래키로 갖는 모든 평가 항목 반환<br>
     * 평가항목 이름으로 정렬
     * @param electronicDeviceId 전자제품 Id
     * @return 전자제품 Id를 외래키로 갖는 모든 평가 항목 반환
     */
    @Transactional(readOnly = true)
    public List<EvaluationItem> findAllByElectronicDeviceId(Long electronicDeviceId){
        return itemRepository.findAllByElectronicDeviceIdOrderByName(electronicDeviceId);
    }

    /**
     * 평가항목 이름을 변경
     * @param id 변경할 평가항목의 id
     * @param name 변경할 이름
     */
    public void updateName(Long id, String name){
        EvaluationItem findEvaluationItem = itemRepository.findById(id).orElseThrow();
        findEvaluationItem.updateName(name);
    }

    /**
     * 평가항목 삭제
     * @param id 삭제할 평가항목의 id
     */
    public void delete(Long id){
        itemRepository.deleteById(id);
    }
}
