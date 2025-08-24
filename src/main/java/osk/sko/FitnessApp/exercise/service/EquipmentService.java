package osk.sko.FitnessApp.exercise.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import osk.sko.FitnessApp.exercise.model.Equipment;
import osk.sko.FitnessApp.exercise.repository.EquipmentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public List<Equipment> getAllEquipments() {
        return equipmentRepository.findAllByOrderByNameAsc();
    }
}
