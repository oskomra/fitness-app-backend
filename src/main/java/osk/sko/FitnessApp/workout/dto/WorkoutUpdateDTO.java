package osk.sko.FitnessApp.workout.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WorkoutUpdateDTO {
    private String title;
    private String description;
}
