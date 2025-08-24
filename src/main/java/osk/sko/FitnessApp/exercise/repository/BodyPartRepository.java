package osk.sko.FitnessApp.exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import osk.sko.FitnessApp.exercise.model.BodyPart;

import java.util.Optional;

@Repository
public interface BodyPartRepository extends JpaRepository<BodyPart,Long> {
    Optional<BodyPart> findByName(String name);
}
