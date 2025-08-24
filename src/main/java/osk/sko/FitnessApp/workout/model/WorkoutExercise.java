package osk.sko.FitnessApp.workout.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import osk.sko.FitnessApp.exercise.model.Exercise;

@Data
@NoArgsConstructor
@Entity
@Table(name = "workout_exercises")
public class WorkoutExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int sets;
    private int reps;
    private double weight;

    @ManyToOne
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;
}
