package osk.sko.FitnessApp.workout.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "workout_exercise_sets")
public class WorkoutExerciseSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int setNumber;
    private int reps;
    private double weight;

    @ManyToOne
    @JoinColumn(name = "workout_exercise_id", nullable = false)
    private WorkoutExercise workoutExercise;

    public WorkoutExerciseSet(int setNumber, int reps, double weight, WorkoutExercise workoutExercise) {
        this.setNumber = setNumber;
        this.reps = reps;
        this.weight = weight;
        this.workoutExercise = workoutExercise;
    }

    public WorkoutExerciseSet(int setNumber, WorkoutExercise workoutExercise) {
        this.setNumber = setNumber;
        this.workoutExercise = workoutExercise;
    }

}
