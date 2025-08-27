package osk.sko.FitnessApp.workout.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import osk.sko.FitnessApp.exception.ResourceNotFoundException;
import osk.sko.FitnessApp.exercise.model.Exercise;
import osk.sko.FitnessApp.exercise.repository.ExerciseRepository;
import osk.sko.FitnessApp.user.model.User;
import osk.sko.FitnessApp.user.service.UserDetailsServiceImpl;
import osk.sko.FitnessApp.workout.dto.WorkoutExerciseDTO;
import osk.sko.FitnessApp.workout.mapper.WorkoutExerciseMapper;
import osk.sko.FitnessApp.workout.model.Workout;
import osk.sko.FitnessApp.workout.model.WorkoutExercise;
import osk.sko.FitnessApp.workout.repository.WorkoutExerciseRepository;
import osk.sko.FitnessApp.workout.repository.WorkoutRepository;

@Service
@RequiredArgsConstructor
public class WorkoutExerciseService {

    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final WorkoutExerciseMapper workoutExerciseMapper;

    public WorkoutExerciseDTO addExerciseToActiveWorkout(String exerciseId) {
        User currentUser = userDetailsService.getCurrentUser();
        Workout workout = workoutRepository.findByUserIdAndEndDateIsNull(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No active workout found for this user"));

        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found"));

        WorkoutExercise workoutExercise = new WorkoutExercise();
        workoutExercise.setExercise(exercise);
        workoutExercise.setWorkout(workout);

        workoutExerciseRepository.save(workoutExercise);
        return workoutExerciseMapper.toDTO(workoutExercise);

    }

}
