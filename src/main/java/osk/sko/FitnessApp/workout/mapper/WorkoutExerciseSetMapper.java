package osk.sko.FitnessApp.workout.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import osk.sko.FitnessApp.workout.dto.WorkoutExerciseSetDTO;
import osk.sko.FitnessApp.workout.model.WorkoutExerciseSet;

@Mapper(componentModel = "spring")
public interface WorkoutExerciseSetMapper {

    @Mapping(target = "workoutExerciseId", source = "workoutExercise.id")
    WorkoutExerciseSetDTO toDto(WorkoutExerciseSet workoutExerciseSet);

    @Mapping(target = "workoutExercise.id", source = "workoutExerciseId")
    WorkoutExerciseSet toEntity(WorkoutExerciseSetDTO workoutExerciseSetDTO);
}
