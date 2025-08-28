package osk.sko.FitnessApp.workout.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import osk.sko.FitnessApp.workout.dto.WorkoutDTO;
import osk.sko.FitnessApp.workout.dto.WorkoutUpdateDTO;
import osk.sko.FitnessApp.workout.service.WorkoutService;

import java.util.List;

@RestController
@RequestMapping("/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @GetMapping
    public ResponseEntity<List<WorkoutDTO>> getAllWorkouts() {
        return ResponseEntity.ok(workoutService.getAllWorkouts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutDTO> getWorkoutById(@PathVariable Long id) {
        return ResponseEntity.ok(workoutService.getWorkoutById(id));
    }

    @GetMapping("/active")
    public ResponseEntity<WorkoutDTO> getActiveWorkout() {
        return  ResponseEntity.ok(workoutService.getActiveWorkout());
    }

    @PostMapping("/start")
    public ResponseEntity<WorkoutDTO> startWorkout() {
        return ResponseEntity.ok(workoutService.startWorkout());
    }

    @PostMapping("/end")
    public ResponseEntity<WorkoutDTO> endWorkout() {
        return ResponseEntity.ok(workoutService.endWorkout());
    }

    @PatchMapping("/active")
    public ResponseEntity<WorkoutDTO> updateActiveWorkout(@RequestBody WorkoutUpdateDTO workoutUpdateDTO) {
        return ResponseEntity.ok(workoutService.updateActiveWorkout(workoutUpdateDTO));
    }


}
