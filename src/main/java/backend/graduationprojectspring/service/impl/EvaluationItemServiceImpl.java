package backend.graduationprojectspring.service.impl;

import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.entity.EvaluationItem;
import backend.graduationprojectspring.exception.HttpError;
import backend.graduationprojectspring.repository.ElectronicDeviceRepo;
import backend.graduationprojectspring.repository.EvaluationItemRepo;
import backend.graduationprojectspring.service.EvaluationItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EvaluationItemServiceImpl implements EvaluationItemService {
    private final EvaluationItemRepo evalItemRepository;
    private final ElectronicDeviceRepo deviceRepository;

    @Override
    public EvaluationItem create(String name, Long deviceId){
        if(evalItemRepository.existsByNameAndElectronicDeviceId(name, deviceId)){
            throw new HttpError("이미 존재하는 평가 항목입니다.", HttpStatus.CONFLICT);
        }
        ElectronicDevice device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new HttpError("해당 하는 전자제품이 없습니다.", HttpStatus.UNPROCESSABLE_ENTITY));
        return evalItemRepository.save(new EvaluationItem(name, device));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EvaluationItem> findAllByElectronicDeviceId(Long electronicDeviceId){
        return evalItemRepository.findAllByElectronicDeviceIdOrderByName(electronicDeviceId);
    }

    @Override
    public void updateName(Long id, String name){
        EvaluationItem findEvaluationItem = evalItemRepository.findById(id)
                .orElseThrow(() -> new HttpError("해당하는 평가 항목이 없습니다.", HttpStatus.UNPROCESSABLE_ENTITY));
        if(evalItemRepository.existsByNameAndElectronicDeviceId(name, findEvaluationItem.getElectronicDevice().getId())){
            throw new HttpError("이미 존재하는 평가 항목입니다.", HttpStatus.CONFLICT);
        }
        findEvaluationItem.updateName(name);
    }

    @Override
    public void delete(Long id){
        evalItemRepository.deleteById(id);
    }
}
