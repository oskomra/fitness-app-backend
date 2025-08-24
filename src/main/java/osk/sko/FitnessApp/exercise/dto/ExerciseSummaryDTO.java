package osk.sko.FitnessApp.exercise.dto;

import osk.sko.FitnessApp.exercise.model.Equipment;
import osk.sko.FitnessApp.exercise.model.Muscle;

import java.util.Set;

public record ExerciseSummaryDTO(
        String exerciseId,
        String name,
        String gifUrl,
        Set<Muscle> targetMuscles,
        Set<Equipment> equipments
) {}
