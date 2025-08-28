package osk.sko.FitnessApp.workout.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import osk.sko.FitnessApp.workout.dto.WorkoutExerciseDTO;
import osk.sko.FitnessApp.workout.service.WorkoutExerciseService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workout-exercises")
public class WorkoutExerciseController {

    private final WorkoutExerciseService workoutExerciseService;


    @PostMapping("/{exerciseId}")
    public ResponseEntity<WorkoutExerciseDTO> addExerciseToActiveWorkout(@PathVariable String exerciseId) {
        return ResponseEntity.ok(workoutExerciseService.addExerciseToActiveWorkout(exerciseId));
    }

    @DeleteMapping("/{workoutExerciseId}")
    public ResponseEntity<Void> removeExerciseFromActiveWorkout(@PathVariable long workoutExerciseId) {
        workoutExerciseService.removeExerciseFromActiveWorkout(workoutExerciseId);
        return ResponseEntity.noContent().build();
    }
}
