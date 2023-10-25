package backend.graduationprojectspring.service.impl;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.entity.EvaluationItem;
import backend.graduationprojectspring.entity.Image;
import backend.graduationprojectspring.exception.DuplicateException;
import backend.graduationprojectspring.exception.NotExistsException;
import backend.graduationprojectspring.repository.*;
import backend.graduationprojectspring.repository.query.ElectronicDeviceQueryRepo;
import backend.graduationprojectspring.repository.query.EvaluationQueryRepo;
import backend.graduationprojectspring.service.ElectronicDeviceService;
import backend.graduationprojectspring.service.dto.DeviceDetailAndAvgDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class ElectronicDeviceServiceImpl implements ElectronicDeviceService {
    private final ElectronicDeviceRepo deviceRepo;
    private final ElectronicDeviceQueryRepo deviceQueryRepo;
    private final CategoryRepo categoryRepo;
    private final ImageRepo imageRepo;
    private final EvaluationQueryRepo evalQueryRepo;

    @Override
    public ElectronicDevice create(String name, Long categoryId){
        if(deviceRepo.existsByName(name)){
            throw new DuplicateException("같은 이름으로 이미 존재하는 전자제품이 있습니다.");
        }
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new NotExistsException("해당 하는 카테고리가 없습니다."));

        return deviceRepo.save(new ElectronicDevice(name, category));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ElectronicDevice> pagingJoinCategory(int page, int size){
        return deviceQueryRepo.pagingJoinCategory(page, size);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ElectronicDevice> pagingJoinCategoryAndEvalItem(int page, int size, String nameCondition, String categoryCondition){
        return deviceQueryRepo.pagingJoinCategoryAndEvalItem(page, size, nameCondition, categoryCondition);
    }

    @Override
    @Transactional(readOnly = true)
    public long totalCount(){
        return deviceRepo.count();
    }

    @Override
    public void update(Long deviceId, String updateDeviceName, Long updateCategoryId){
        ElectronicDevice findDevice = deviceRepo.findById(deviceId)
                .orElseThrow(() -> new NotExistsException("존재 하지 않는 전자제품 입니다."));
        Category updateCategory = categoryRepo.findById(updateCategoryId)
                .orElseThrow(() -> new NotExistsException("존재 하지 않는 카테고리 입니다."));
        if(!findDevice.getName().equals(updateDeviceName) && deviceRepo.existsByName(updateDeviceName)){
            throw new DuplicateException("해당 이름으로 이미 존재하는 전자제품이 있습니다.");
        }

        findDevice.updateNameAndCategory(updateDeviceName, updateCategory);
    }

    @Override
    public void delete(Long id){
        deviceRepo.deleteById(id);
    }

    /**
     * 전자제품 이미지 설정
     * @param deviceId 이미지를 설정할 전자제품 id
     * @param imageId 설정할 이미지의 id
     * @throws NotExistsException 해당하는 전자제품이나 이미지가 없으면 발생
     */
    @Override
    public void setImage(Long deviceId, Long imageId){
        ElectronicDevice device = deviceRepo.findById(deviceId)
                .orElseThrow(() -> new NotExistsException("존재 하지 않는 전자제품 입니다."));
        Image image = imageRepo.findById(imageId)
                .orElseThrow(() -> new NotExistsException("존재 하지 않는 이미지 입니다."));
        image.setVisible(true);
        image.setElectronicDevice(device);
    }

    @Override
    @Transactional(readOnly = true)
    public DeviceDetailAndAvgDto findOneDetail(Long id){
        ElectronicDevice device = deviceQueryRepo
                .findOneJoinCategoryAndEvalItem(id)
                .orElseThrow(() -> new NotExistsException("해당하는 전자제품이 없습니다."));

        List<Long> evalItemIdList = device.getEvaluationItemList()
                .stream()
                .map(EvaluationItem::getId)
                .toList();

        Map<Long, Double> avgGroupByEvalItemMap = evalQueryRepo.avgGroupByEvalItem(evalItemIdList);

        return new DeviceDetailAndAvgDto(device, avgGroupByEvalItemMap);
    }

    @Override
    public Long countByCondition(String nameCondition, String categoryCondition) {
        return deviceQueryRepo.countByCondition(nameCondition, categoryCondition);
    }

    @Override
    public Map<Long, Double> avgGroupByDevice(List<Long> deviceIdList) {
        return evalQueryRepo.avgGroupByDevice(deviceIdList);
    }
}
