package osk.sko.FitnessApp.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import osk.sko.FitnessApp.workout.dto.WorkoutDTO;
import osk.sko.FitnessApp.workout.model.WorkoutExerciseSet;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private long id;

    @Email
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Last Name is required")
    private String lastName;

    @NotBlank(message = "Phone Number is required")
    private String phoneNumber;

    private String authority;

    private List<WorkoutDTO> workouts;
}
