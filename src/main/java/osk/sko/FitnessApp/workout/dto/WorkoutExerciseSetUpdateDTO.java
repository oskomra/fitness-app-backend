package osk.sko.FitnessApp.workout.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WorkoutExerciseSetUpdateDTO {

    @Min(0)
    private Integer reps;

    @Min(0)
    private Double weight;
}
