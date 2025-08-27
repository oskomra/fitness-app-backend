package osk.sko.FitnessApp.workout.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import osk.sko.FitnessApp.exception.ResourceAlreadyExistsException;
import osk.sko.FitnessApp.exception.ResourceNotFoundException;
import osk.sko.FitnessApp.user.model.User;
import osk.sko.FitnessApp.user.service.UserDetailsServiceImpl;
import osk.sko.FitnessApp.workout.dto.WorkoutDTO;
import osk.sko.FitnessApp.workout.dto.WorkoutUpdateDTO;
import osk.sko.FitnessApp.workout.mapper.WorkoutMapper;
import osk.sko.FitnessApp.workout.model.Workout;
import osk.sko.FitnessApp.workout.repository.WorkoutRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final WorkoutMapper workoutMapper;

    public List<WorkoutDTO> getAllWorkouts() {
        User currentUser = userDetailsService.getCurrentUser();
        return workoutRepository
                .findAllByUserId(currentUser.getId())
                .stream()
                .map(workoutMapper::toDTO)
                .toList();
    }

    public WorkoutDTO getWorkoutById(Long workoutId) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout not found"));
        return workoutMapper.toDTO(workout);

    }

    public WorkoutDTO getActiveWorkout() {
        User currentUser = userDetailsService.getCurrentUser();
        Workout workout = workoutRepository.findByUserIdAndEndDateIsNull(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No active workout found for this user"));
        return workoutMapper.toDTO(workout);
    }

    public WorkoutDTO startWorkout() {
        User currentUser = userDetailsService.getCurrentUser();

        if (workoutRepository.findByUserIdAndEndDateIsNull(currentUser.getId()).isPresent()) {
            throw new ResourceAlreadyExistsException("Workout already exists");
        }

        Workout workout = new Workout();
        workout.setUser(currentUser);
        workout.setStartDate(java.time.LocalDateTime.now());

        workoutRepository.save(workout);
        return workoutMapper.toDTO(workout);
    }

    public WorkoutDTO endWorkout() {
        User currentUser = userDetailsService.getCurrentUser();
        Workout workout = workoutRepository.findByUserIdAndEndDateIsNull(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No active workout found for this user"));

        workout.setEndDate(java.time.LocalDateTime.now());
        workoutRepository.save(workout);
        return workoutMapper.toDTO(workout);
    }

    public WorkoutDTO updateActiveWorkout(WorkoutUpdateDTO workoutUpdateDTO) {
        User currentUser = userDetailsService.getCurrentUser();
        Workout workout = workoutRepository.findByUserIdAndEndDateIsNull(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No active workout found for this user"));

        if (workoutUpdateDTO.getTitle() != null) {
            workout.setTitle(workoutUpdateDTO.getTitle());
        }

        if (workoutUpdateDTO.getDescription() != null) {
            workout.setDescription(workoutUpdateDTO.getDescription());
        }

        workoutRepository.save(workout);
        return workoutMapper.toDTO(workout);
    }


}
