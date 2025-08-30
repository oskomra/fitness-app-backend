package osk.sko.FitnessApp.workout.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutExerciseDTO {

    private Long id;
    private List<WorkoutExerciseSetDTO> sets;
    private Long workoutId;
    private String exerciseId;
}
