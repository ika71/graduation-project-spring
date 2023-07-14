package backend.graduationprojectspring.controller.admin;

import backend.graduationprojectspring.entity.EvaluationItem;
import backend.graduationprojectspring.service.EvaluationItemService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @GetMapping
    public EvaluationItemViewResult evaluationItemView(
            @RequestParam(name = "deviceId") Long deviceId){
        List<EvaluationItem> findItemList = itemService.findAllByElectronicDeviceId(deviceId);
        return new EvaluationItemViewResult(findItemList);
    }
    @PostMapping
    public ResponseEntity<?> evaluationItemCreate(
            @RequestBody @Validated EvaluationItemCreateDto createDto){
        itemService.create(
                createDto.getName(),
                createDto.getElectronicDeviceId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?> evaluationItemUpdateName(
            @PathVariable(name = "id")Long id,
            @RequestBody @Validated EvaluationItemUpdateDto updateDto){
        itemService.updateName(id, updateDto.getName());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> evaluationItemDelete(@PathVariable(name = "id") Long id){
        itemService.delete(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
    @Getter
    private static class EvaluationItemViewResult{
        private final List<EvaluationItemViewDto> evaluationItemList;

        public EvaluationItemViewResult(List<EvaluationItem> evaluationItemList) {
            this.evaluationItemList = evaluationItemList
                    .stream()
                    .map(EvaluationItemViewDto::new)
                    .toList();
        }
    }

    @Getter
    private static class EvaluationItemViewDto {
        private final Long id;
        private final String name;

        public EvaluationItemViewDto(EvaluationItem evaluationItem) {
            this.id = evaluationItem.getId();
            this.name = evaluationItem.getName();
        }
    }

    @Getter
    private static class EvaluationItemCreateDto {
        @NotNull
        private Long electronicDeviceId;
        @NotBlank
        private String name;
    }

    @Getter
    private static class EvaluationItemUpdateDto {
        @NotBlank
        private String name;
    }
}
