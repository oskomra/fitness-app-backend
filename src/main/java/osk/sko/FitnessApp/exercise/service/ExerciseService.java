package osk.sko.FitnessApp.exercise.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import osk.sko.FitnessApp.exercise.dto.ExerciseSummaryDTO;
import osk.sko.FitnessApp.exercise.model.Exercise;
import osk.sko.FitnessApp.exercise.repository.ExerciseRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public Exercise getExerciseById(String exerciseId) {
        return exerciseRepository.findById(exerciseId)
                .orElseThrow(RuntimeException::new);
    }

    public List<ExerciseSummaryDTO> getAllExercises() {
        List<Exercise> exercises = exerciseRepository.findAllWithTargetMuscles();
        return exercises.stream()
                .map(e -> new ExerciseSummaryDTO(
                        e.getExerciseId(),
                        e.getName(),
                        e.getGifUrl(),
                        e.getTargetMuscles(),
                        e.getEquipments()
                ))
                .toList();
    }
}
