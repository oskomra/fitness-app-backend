package osk.sko.FitnessApp.exercise.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import osk.sko.FitnessApp.exercise.dto.ExerciseSummaryDTO;
import osk.sko.FitnessApp.exercise.model.Exercise;
import osk.sko.FitnessApp.exercise.service.ExerciseService;

import java.util.List;

@RestController
@RequestMapping("/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @GetMapping("/{exerciseId}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable String exerciseId){
        return ResponseEntity.ok(exerciseService.getExerciseById(exerciseId));
    }

    @GetMapping
    public ResponseEntity<List<ExerciseSummaryDTO>> getAllExercises(){
        return ResponseEntity.ok(exerciseService.getAllExercises());
    }


}
