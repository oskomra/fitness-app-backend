package osk.sko.FitnessApp.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private long id;
    private String email;
    private String name;
    private String lastName;
    private String phone;
    private String password;
    private String authority;
}
