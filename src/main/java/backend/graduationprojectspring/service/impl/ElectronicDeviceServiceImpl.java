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

    /**
     * 전자제품 데이터베이스에 저장
     * @param name 저장할 전자제품 이름
     * @param categoryId 저장할 전자제품이 속하는 카테고리 Id
     * @return 저장된 전자제품
     * @throws DuplicateException 같은 이름으로 존재하는 전자제품이 이미 있을 경우
     */
    @Override
    public ElectronicDevice create(String name, Long categoryId){
        if(deviceRepo.existsByName(name)){
            throw new DuplicateException("같은 이름으로 이미 존재하는 전자제품이 있습니다.");
        }
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new NotExistsException("해당 하는 카테고리가 없습니다."));

        return deviceRepo.save(new ElectronicDevice(name, category));
    }

    /**
     * 전자제품 페이지 조회(전자제품 카테고리 정보 fetch join)<br>
     * 전자제품 이름으로 정렬됨<br>
     * @param page 현재 보여줄 페이지 위치
     * @param size 얼마만큼 보여줄지 크기
     * @return 조회된 ElectronicDevice List 반환
     */
    @Override
    @Transactional(readOnly = true)
    public List<ElectronicDevice> pagingJoinCategory(int page, int size){
        return deviceQueryRepo.pagingJoinCategory(page, size);
    }

    /**
     * 전자제품 페이지 조회
     * (전자제품 카테고리 fetch join, 평가항목 fetch left join)<br>
     * 전자제품 생성일로 내림차순 정렬됨<br>
     * @param page 현재 보여줄 페이지 위치
     * @param size 얼마만큼 보여줄지 크기
     * @return 조회된 ElectronicDevice List 반환
     */
    @Override
    @Transactional(readOnly = true)
    public List<ElectronicDevice> pagingJoinCategoryAndEvalItem(int page, int size){
        return deviceQueryRepo.pagingJoinCategoryAndEvalItem(page, size);
    }

    /**
     * 전자제품 전체 개수 반환
     * @return 전자제품 전체 수
     */
    @Override
    @Transactional(readOnly = true)
    public Long totalCount(){
        return deviceRepo.count();
    }

    /**
     * 전자제품 수정
     * @param deviceId 수정할 전자제품의 Id
     * @param updateDeviceName 수정할 이름
     * @param updateCategoryId 전자제품이 수정 후에 속할 카테고리 Id
     * @throws NotExistsException 전자제품이나 카테고리가 이미 없으면 발생
     * @throws DuplicateException 수정할 이름을 전자제품이 이미 존재하면 발생
     */
    @Override
    public void update(Long deviceId, String updateDeviceName, Long updateCategoryId){
        ElectronicDevice findDevice = deviceRepo.findById(deviceId)
                .orElseThrow(() -> new NotExistsException("존재 하지 않는 전자제품 입니다."));
        Category updateCategory = categoryRepo.findById(updateCategoryId)
                .orElseThrow(() -> new NotExistsException("존재 하지 않는 카테고리 입니다."));
        if(deviceRepo.existsByName(updateDeviceName)){
            throw new DuplicateException("해당 이름으로 이미 존재하는 전자제품이 있습니다.");
        }

        findDevice.updateNameAndCategory(updateDeviceName, updateCategory);
    }

    /**
     * 전자제품 삭제
     * @param id 삭제할 전자제품의 id
     */
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
        device.setImage(image);
    }

    /**
     * 전자제품 하나 조회<br>
     * 해당 전자제품은 카테고리, 평가항목을 fetch join 함<br>
     * 전자제품이 가지고 있는 평가항목을 group by하여 평점 score의 평균을 계산함<br>
     * 계산 결과는 Map으로 반환 key = 평가항목 Id, value = score의 평균
     * @param id 조회할 전자제품 Id
     * @return 조회된 결과는 Dto로 반환
     * @throws NotExistsException 해당하는 전자제품이 없으면 발생
     */
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
}
