package osk.sko.FitnessApp.workout.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import osk.sko.FitnessApp.user.model.User;
import osk.sko.FitnessApp.user.service.UserDetailsServiceImpl;
import osk.sko.FitnessApp.workout.model.Workout;
import osk.sko.FitnessApp.workout.repository.WorkoutRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserDetailsServiceImpl userDetailsService;

    public List<Workout> getUserWorkouts() {
        User currentUser = userDetailsService.getCurrentUser();
        return workoutRepository.findByUserId(currentUser.getId());
    }
}
