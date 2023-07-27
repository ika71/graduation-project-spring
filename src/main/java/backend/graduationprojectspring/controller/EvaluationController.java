package backend.graduationprojectspring.controller;

import backend.graduationprojectspring.service.EvaluationService;
import backend.graduationprojectspring.service.dto.EvalItemAndEvaluationDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/evaluation")
@RequiredArgsConstructor
public class EvaluationController {
    private final EvaluationService evaluationService;

    @GetMapping
    public EvaluationFindResultDto evaluationFind(@RequestParam("deviceId")Long deviceId){
        String memberId = SecurityContextHolder.getContext().getAuthentication().getName();

        List<EvalItemAndEvaluationDto> evalItemAndEvaluationDtoList =
                evaluationService.
                        findAllByMemberIdAndDeviceId(memberId, deviceId);

        return new EvaluationFindResultDto(evalItemAndEvaluationDtoList);
    }

    @PutMapping
    public ResponseEntity<?> evaluationPut(
            @RequestBody @Validated EvaluationPutResultDto evaluationPutResultDto){
        String memberId = SecurityContextHolder.getContext().getAuthentication().getName();

        evaluationService.put(memberId, evaluationPutResultDto.toEvalScoreMap());

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Getter
    @ToString
    public static class EvaluationPutResultDto {
        @NotNull
        private List<EvaluationPutDto> evaluationPutDtoList;

        public Map<Long, Integer> toEvalScoreMap(){
            return evaluationPutDtoList
                    .stream()
                    .collect(Collectors.toMap(
                            EvaluationPutDto::getEvalItemId,
                            EvaluationPutDto::getEvaluationScore
                    ));
        }
    }
    @Getter
    @ToString
    private static class EvaluationPutDto {
        @NotNull
        private Long evalItemId;
        @NotNull
        private Integer evaluationScore;
    }
    @Getter
    @ToString
    public static class EvaluationFindResultDto{
        private final List<EvaluationFindDto> evaluationFindDtoList;

        public EvaluationFindResultDto(List<EvalItemAndEvaluationDto> evaluationFindDtoList) {
            this.evaluationFindDtoList = evaluationFindDtoList
                    .stream()
                    .map(EvaluationFindDto::new)
                    .toList();
        }
    }
    @Getter
    @ToString
    private static class EvaluationFindDto{
        private final Long evalItemId;
        private final String evalItemName;
        private final Integer evalScore;

        public EvaluationFindDto(EvalItemAndEvaluationDto evalItemAndEvaluationDto) {
            this.evalItemId = evalItemAndEvaluationDto.getEvalItemId();
            this.evalItemName = evalItemAndEvaluationDto.getEvalItemName();
            this.evalScore = evalItemAndEvaluationDto.getEvalScore();
        }
    }
}
