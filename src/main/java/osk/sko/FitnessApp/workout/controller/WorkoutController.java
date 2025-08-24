package osk.sko.FitnessApp.workout.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import osk.sko.FitnessApp.workout.model.Workout;
import osk.sko.FitnessApp.workout.service.WorkoutService;

import java.util.List;

@RestController
@RequestMapping("/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @GetMapping
    public ResponseEntity<List<Workout>> getUserWorkouts() {
        return ResponseEntity.ok(workoutService.getUserWorkouts());
    }
}
