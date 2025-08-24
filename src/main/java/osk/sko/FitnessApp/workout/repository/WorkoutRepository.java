package osk.sko.FitnessApp.workout.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import osk.sko.FitnessApp.workout.model.Workout;

import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Integer> {
    List<Workout> findByUserId(long userId);
}
