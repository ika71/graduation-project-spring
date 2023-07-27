package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.entity.EvaluationItem;
import backend.graduationprojectspring.exception.DuplicateException;
import backend.graduationprojectspring.exception.NotExistsException;
import backend.graduationprojectspring.repository.ElectronicDeviceRepo;
import backend.graduationprojectspring.repository.EvaluationItemRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EvaluationItemService {
    private final EvaluationItemRepo evalItemRepository;
    private final ElectronicDeviceRepo deviceRepository;

    /**
     * 평가항목 데이터베이스에 저장
     * @param name 저장할 평가항목 이름
     * @param deviceId 평가항목이 속하는 전자제품 id
     * @return 저장된 평가항목
     * @throws DuplicateException 전자제품에 같은 이름으로 평가항목이 이미 있을 경우
     */
    public EvaluationItem create(String name, Long deviceId){
        if(evalItemRepository.existsByNameAndElectronicDeviceId(name, deviceId)){
            throw new DuplicateException("이미 존재하는 평가 항목입니다.");
        }
        ElectronicDevice device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new NotExistsException("해당 하는 전자제품이 없습니다."));
        return evalItemRepository.save(new EvaluationItem(name, device));
    }

    /**
     * 전자제품 Id를 외래키로 갖는 모든 평가 항목 반환<br>
     * 평가항목 이름으로 정렬
     * @param electronicDeviceId 전자제품 Id
     * @return 전자제품 Id를 외래키로 갖는 모든 평가 항목 반환
     */
    @Transactional(readOnly = true)
    public List<EvaluationItem> findAllByElectronicDeviceId(Long electronicDeviceId){
        return evalItemRepository.findAllByElectronicDeviceIdOrderByName(electronicDeviceId);
    }

    /**
     * 평가항목 이름을 변경
     * @param id 변경할 평가항목의 id
     * @param name 변경할 이름
     * @throws NotExistsException 해당하는 평가항목이 없으면 발생
     * @throws DuplicateException 이미 존재하는 이름의 평가항목으로 수정하려 할 때 발생
     */
    public void updateName(Long id, String name){
        EvaluationItem findEvaluationItem = evalItemRepository.findById(id)
                .orElseThrow(() -> new NotExistsException("해당하는 평가 항목이 없습니다."));
        if(evalItemRepository.existsByNameAndElectronicDeviceId(name, id)){
            throw new DuplicateException("이미 존재하는 평가 항목입니다.");
        }
        findEvaluationItem.updateName(name);
    }

    /**
     * 평가항목 삭제
     * @param id 삭제할 평가항목의 id
     * @throws IllegalArgumentException id가 null일 경우 발생
     */
    public void delete(Long id){
        evalItemRepository.deleteById(id);
    }
}
