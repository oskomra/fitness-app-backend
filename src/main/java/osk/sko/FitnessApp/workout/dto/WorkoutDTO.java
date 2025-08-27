package osk.sko.FitnessApp.workout.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class WorkoutDTO {
    private Long id;
    private String title;
    private String description;
    private String startDate;
    private String endDate;
    private Long userId;
    private Set<WorkoutExerciseDTO> exercises;
}
