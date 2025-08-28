package osk.sko.FitnessApp.workout.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import osk.sko.FitnessApp.exception.ResourceNotFoundException;
import osk.sko.FitnessApp.workout.dto.WorkoutExerciseSetDTO;
import osk.sko.FitnessApp.workout.dto.WorkoutExerciseSetUpdateDTO;
import osk.sko.FitnessApp.workout.mapper.WorkoutExerciseSetMapper;
import osk.sko.FitnessApp.workout.model.WorkoutExercise;
import osk.sko.FitnessApp.workout.model.WorkoutExerciseSet;
import osk.sko.FitnessApp.workout.repository.WorkoutExerciseRepository;
import osk.sko.FitnessApp.workout.repository.WorkoutExerciseSetRepository;

@Service
@RequiredArgsConstructor
public class WorkoutExerciseSetService {

    private final WorkoutExerciseSetRepository workoutExerciseSetRepository;
    private final WorkoutExerciseSetMapper workoutExerciseSetMapper;
    private final WorkoutExerciseRepository workoutExerciseRepository;

    public WorkoutExerciseSetDTO updateExerciseSet(long id, WorkoutExerciseSetUpdateDTO workoutExerciseSetUpdateDTO) {
        WorkoutExerciseSet workoutExerciseSet = workoutExerciseSetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workout exercise set not found"));

        if (workoutExerciseSetUpdateDTO.getReps() != null) {
            workoutExerciseSet.setReps(workoutExerciseSetUpdateDTO.getReps());
        }
        if (workoutExerciseSetUpdateDTO.getWeight() != null) {
            workoutExerciseSet.setWeight(workoutExerciseSetUpdateDTO.getWeight());
        }

        workoutExerciseSetRepository.save(workoutExerciseSet);

        return workoutExerciseSetMapper.toDto(workoutExerciseSet);
    }

    public void deleteExerciseSet(long id) {
        WorkoutExerciseSet workoutExerciseSet = workoutExerciseSetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workout exercise set not found"));

        workoutExerciseSetRepository.delete(workoutExerciseSet);

        WorkoutExercise workoutExercise = workoutExerciseSet.getWorkoutExercise();
        workoutExercise.recountSetNumbers();
        workoutExerciseRepository.save(workoutExercise);

    }

    public WorkoutExerciseSetDTO addExerciseSet(long exerciseWorkoutId, WorkoutExerciseSetUpdateDTO workoutExerciseSetUpdateDTO) {
        WorkoutExercise workoutExercise = workoutExerciseRepository
                .findById(exerciseWorkoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout exercise not found"));

        int setNumber = workoutExercise.getSets().size() + 1;
        WorkoutExerciseSet workoutExerciseSet = new WorkoutExerciseSet(setNumber, workoutExercise);

        if(workoutExerciseSetUpdateDTO != null) {
            if (workoutExerciseSetUpdateDTO.getReps() != null) {
                workoutExerciseSet.setReps(workoutExerciseSetUpdateDTO.getReps());
            }
            if (workoutExerciseSetUpdateDTO.getWeight() != null) {
                workoutExerciseSet.setWeight(workoutExerciseSetUpdateDTO.getWeight());
            }
        }

        workoutExerciseSetRepository.save(workoutExerciseSet);

        return workoutExerciseSetMapper.toDto(workoutExerciseSet);
    }
}
