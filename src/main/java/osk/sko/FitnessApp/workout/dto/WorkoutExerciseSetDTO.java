package osk.sko.FitnessApp.workout.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutExerciseSetDTO {
    private Long id;
    private int setNumber;
    private int reps;
    private double weight;
    private Long workoutExerciseId;
}
