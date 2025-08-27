package osk.sko.FitnessApp.workout.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import osk.sko.FitnessApp.workout.model.WorkoutExerciseSet;

@Repository
public interface WorkoutExerciseSetRepository extends JpaRepository<WorkoutExerciseSet, Long> {
}
