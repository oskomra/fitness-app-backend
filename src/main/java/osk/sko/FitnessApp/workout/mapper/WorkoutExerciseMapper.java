package osk.sko.FitnessApp.workout.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import osk.sko.FitnessApp.workout.dto.WorkoutExerciseDTO;
import osk.sko.FitnessApp.workout.model.WorkoutExercise;
import osk.sko.FitnessApp.workout.model.WorkoutExerciseSet;

@Mapper(componentModel = "spring", uses = WorkoutExerciseSetMapper.class)
public interface WorkoutExerciseMapper {

    @Mapping(target = "workoutId", source = "workout.id")
    @Mapping(target = "exerciseId", source = "exercise.exerciseId")
    WorkoutExerciseDTO toDTO(WorkoutExercise workoutExercise);

    @Mapping(target = "workout.id", source = "workoutId")
    @Mapping(target = "exercise.exerciseId", source = "exerciseId")
    WorkoutExercise toEntity(WorkoutExerciseDTO workoutExerciseDTO);
}
