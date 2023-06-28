package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.EvaluationItem;
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

    /**
     * 평가 항목 데이터 베이스에 저장
     * @param evaluationItem 저장할 평가 항목
     * @return 저장된 평가 항목 반환
     */
    public EvaluationItem create(EvaluationItem evaluationItem){
        return itemRepository.save(evaluationItem);
    }

    /**
     * 전자제품 Id를 외래키로 갖는 모든 평가 항목 반환<br>
     * 평가항목 이름으로 정렬
     * @param electronicDeviceId 전자제품 Id
     * @return 전자제품 Id를 외래키로 갖는 모든 평가 항목 반환
     */
    public List<EvaluationItem> findAllByElectronicDeviceId(Long electronicDeviceId){
        return itemRepository.findAllByElectronicDevice_IdOrderByName(electronicDeviceId);
    }
}
