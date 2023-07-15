package backend.graduationprojectspring.controller;

import backend.graduationprojectspring.service.EvaluationService;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/evaluation")
@RequiredArgsConstructor
public class EvaluationController {
    private final EvaluationService evaluationService;

    @PutMapping
    public ResponseEntity<?> evaluationPut(
            @RequestBody @Validated EvaluationPutDto evaluationPutDto){
        String memberId = SecurityContextHolder.getContext().getAuthentication().getName();

        evaluationService.put(memberId, evaluationPutDto.toEvalScoreMap());

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Getter
    @ToString
    private static class EvaluationPutDto{
        @NotNull
        private List<EvaluationDto> evaluationDtoList;

        public Map<Long, Integer> toEvalScoreMap(){
            return evaluationDtoList
                    .stream()
                    .collect(Collectors.toMap(
                            EvaluationDto::getEvalItemId,
                            EvaluationDto::getEvaluationScore
                    ));
        }
    }
    @Getter
    @ToString
    private static class EvaluationDto{
        @NotNull
        private Long evalItemId;
        @NotNull
        private Integer evaluationScore;
    }
}
