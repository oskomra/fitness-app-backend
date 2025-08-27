package osk.sko.FitnessApp.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import osk.sko.FitnessApp.user.dto.UserDTO;
import osk.sko.FitnessApp.user.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {


    UserDTO toDTO(User user);

    @Mapping(target = "password", ignore = true)
    User toEntity(UserDTO userDTO);
}
