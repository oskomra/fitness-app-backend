package osk.sko.FitnessApp.exercise.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import osk.sko.FitnessApp.exercise.model.Muscle;
import osk.sko.FitnessApp.exercise.service.MuscleService;

import java.util.List;

@RestController
@RequestMapping("/muscles")
@RequiredArgsConstructor
public class MuscleController {

    private final MuscleService muscleService;

    @GetMapping
    public ResponseEntity<List<Muscle>> getAllMuscles() {
        return ResponseEntity.ok(muscleService.getAllMuscles());
    }
}
