package osk.sko.FitnessApp.workout.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import osk.sko.FitnessApp.user.mapper.UserMapper;
import osk.sko.FitnessApp.workout.dto.WorkoutDTO;
import osk.sko.FitnessApp.workout.model.Workout;

@Mapper(componentModel = "spring", uses = {WorkoutExerciseMapper.class, UserMapper.class})
public interface WorkoutMapper {

    @Mapping(target = "userId", source = "workout.user.id")
    WorkoutDTO toDTO(Workout workout);

    @Mapping(target = "user.id", source = "userId")
    Workout toEntity(WorkoutDTO workoutDTO);


}
