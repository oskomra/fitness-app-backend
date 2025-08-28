package osk.sko.FitnessApp.workout.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class WorkoutExerciseDTO {

    private Long id;
    private List<WorkoutExerciseSetDTO> sets;
    private Long workoutId;
    private String exerciseId;
}
