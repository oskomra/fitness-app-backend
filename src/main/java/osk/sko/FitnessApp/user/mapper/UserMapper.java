package osk.sko.FitnessApp.user.mapper;

import org.mapstruct.Mapper;
import osk.sko.FitnessApp.user.dto.UserDTO;
import osk.sko.FitnessApp.user.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userToUserDTO(User user);
    User userDTOToUser(UserDTO userDTO);
}
