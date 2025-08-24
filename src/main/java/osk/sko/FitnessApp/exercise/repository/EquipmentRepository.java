package osk.sko.FitnessApp.exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import osk.sko.FitnessApp.exercise.model.Equipment;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment,Long> {
    Optional<Equipment> findByName(String name);
    List<Equipment> findAllByOrderByNameAsc();
}
