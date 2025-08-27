package osk.sko.FitnessApp.workout.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import osk.sko.FitnessApp.workout.model.Workout;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findAllByUserId(Long userId);
    Optional<Workout> findByUserIdAndEndDateIsNull(Long userId);
}
