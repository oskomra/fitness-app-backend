package osk.sko.FitnessApp.workout.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WorkoutUpdateDTO {

    @Size(max = 100, message = "Title must be at most 100 characters long")
    private String title;

    @Size(max = 250, message = "Description must be at most 250 characters long")
    private String description;
}
