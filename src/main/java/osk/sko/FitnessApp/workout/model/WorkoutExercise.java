package osk.sko.FitnessApp.workout.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import osk.sko.FitnessApp.exercise.model.Exercise;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "workout_exercises")
public class WorkoutExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "workoutExercise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutExerciseSet> sets = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;
}
