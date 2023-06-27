package backend.graduationprojectspring.controller.admin.evaluation_item;

import backend.graduationprojectspring.controller.admin.evaluation_item.dto.EvaluationItemViewDto;
import backend.graduationprojectspring.entity.EvaluationItem;
import backend.graduationprojectspring.service.EvaluationItemService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/evaluationitem")
@RequiredArgsConstructor
public class AdminEvaluationItemController {
    private final EvaluationItemService itemService;

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

    @Getter
    private static class EvaluationItemViewResult{
        private final List<EvaluationItemViewDto> evaluationItemList;

        public EvaluationItemViewResult(List<EvaluationItemViewDto> evaluationItemList) {
            this.evaluationItemList = evaluationItemList;
        }
    }
}
