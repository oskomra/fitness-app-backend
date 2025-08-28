package osk.sko.FitnessApp.workout.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import osk.sko.FitnessApp.workout.dto.WorkoutExerciseSetDTO;
import osk.sko.FitnessApp.workout.dto.WorkoutExerciseSetUpdateDTO;
import osk.sko.FitnessApp.workout.service.WorkoutExerciseSetService;

@RestController
@RequestMapping("/workout-exercise-sets")
@RequiredArgsConstructor
public class WorkoutExerciseSetController {

    private final WorkoutExerciseSetService workoutExerciseSetService;

    @PatchMapping("/{id}")
    public ResponseEntity<WorkoutExerciseSetDTO> updateExerciseSet(@PathVariable long id, @RequestBody WorkoutExerciseSetUpdateDTO workoutExerciseSetUpdateDTO) {
        return ResponseEntity.ok(workoutExerciseSetService.updateExerciseSet(id, workoutExerciseSetUpdateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WorkoutExerciseSetDTO> deleteExerciseSet(@PathVariable long id) {
        workoutExerciseSetService.deleteExerciseSet(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{exerciseWorkoutId}")
    public ResponseEntity<WorkoutExerciseSetDTO> addExerciseSet(
            @PathVariable long exerciseWorkoutId,
            @RequestBody(required = false) WorkoutExerciseSetUpdateDTO workoutExerciseSetUpdateDTO) {
        return ResponseEntity.ok(workoutExerciseSetService.addExerciseSet(exerciseWorkoutId, workoutExerciseSetUpdateDTO));
    }
}
