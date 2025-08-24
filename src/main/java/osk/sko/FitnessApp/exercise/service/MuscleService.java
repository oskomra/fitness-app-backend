package osk.sko.FitnessApp.exercise.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import osk.sko.FitnessApp.exercise.model.Muscle;
import osk.sko.FitnessApp.exercise.repository.MuscleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MuscleService {

    private final MuscleRepository muscleRepository;

    public List<Muscle> getAllMuscles() {
        return muscleRepository.findAllByOrderByNameAsc();
    }
}
