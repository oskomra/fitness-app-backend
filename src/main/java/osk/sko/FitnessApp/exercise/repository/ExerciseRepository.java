package osk.sko.FitnessApp.exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import osk.sko.FitnessApp.exercise.model.Exercise;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise,String> {

    @Query("SELECT e FROM Exercise e LEFT JOIN FETCH e.targetMuscles LEFT JOIN FETCH e.equipments ORDER BY e.name ASC")
    List<Exercise> findAllWithTargetMuscles();


}
