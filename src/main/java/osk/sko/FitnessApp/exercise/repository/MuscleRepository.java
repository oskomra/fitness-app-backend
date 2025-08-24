package osk.sko.FitnessApp.exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import osk.sko.FitnessApp.exercise.model.Muscle;

import java.util.List;
import java.util.Optional;

@Repository
public interface MuscleRepository extends JpaRepository<Muscle,Long> {
    Optional<Muscle> findByName(String name);
    List<Muscle> findAllByOrderByNameAsc();
}
