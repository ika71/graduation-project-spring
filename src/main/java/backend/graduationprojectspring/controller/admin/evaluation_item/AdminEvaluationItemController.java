package backend.graduationprojectspring.controller.admin.evaluation_item;

import backend.graduationprojectspring.controller.admin.evaluation_item.dto.EvaluationItemCreateDto;
import backend.graduationprojectspring.controller.admin.evaluation_item.dto.EvaluationItemUpdateDto;
import backend.graduationprojectspring.controller.admin.evaluation_item.dto.EvaluationItemViewDto;
import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.entity.EvaluationItem;
import backend.graduationprojectspring.service.ElectronicDeviceService;
import backend.graduationprojectspring.service.EvaluationItemService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/evaluationitem")
@RequiredArgsConstructor
public class AdminEvaluationItemController {
    private final EvaluationItemService itemService;
    private final ElectronicDeviceService deviceService;

    @GetMapping
    public EvaluationItemViewResult evaluationItemView(
            @RequestParam(name = "deviceId") Long deviceId){
        List<EvaluationItem> findItemList = itemService.findAllByElectronicDeviceId(deviceId);
        List<EvaluationItemViewDto> itemViewDtoList = findItemList
                .stream()
                .map(EvaluationItemViewDto::new)
                .toList();

        return new EvaluationItemViewResult(itemViewDtoList);
    }
    @PostMapping
    public ResponseEntity<?> evaluationItemCreate(
            @RequestBody @Validated EvaluationItemCreateDto createDto){
        ElectronicDevice electronicDevice = deviceService.getReferenceById(createDto.getElectronicDeviceId());
        EvaluationItem evaluationItem = createDto.toEvaluationItem(electronicDevice);

        itemService.create(evaluationItem);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
    @PatchMapping
    public ResponseEntity<?> evaluationItemUpdateName(
            @RequestBody @Validated EvaluationItemUpdateDto updateDto){

        itemService.updateName(updateDto.getId(), updateDto.getName());

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
    @DeleteMapping
    public ResponseEntity<?> evaluationItemDelete(@RequestParam Long id){
        itemService.delete(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
    @Getter
    private static class EvaluationItemViewResult{
        private final List<EvaluationItemViewDto> evaluationItemList;

        public EvaluationItemViewResult(List<EvaluationItemViewDto> evaluationItemList) {
            this.evaluationItemList = evaluationItemList;
        }
    }
}
