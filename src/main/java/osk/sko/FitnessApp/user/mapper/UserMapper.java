package osk.sko.FitnessApp.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import osk.sko.FitnessApp.user.dto.UserDTO;
import osk.sko.FitnessApp.user.model.User;
import osk.sko.FitnessApp.workout.mapper.WorkoutExerciseSetMapper;
import osk.sko.FitnessApp.workout.mapper.WorkoutMapper;

@Mapper(componentModel = "spring", uses = {WorkoutMapper.class, WorkoutExerciseSetMapper.class})
public interface UserMapper {


    UserDTO toDTO(User user);

    @Mapping(target = "password", ignore = true)
    User toEntity(UserDTO userDTO);
}
