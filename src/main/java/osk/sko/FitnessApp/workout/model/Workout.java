package osk.sko.FitnessApp.workout.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import osk.sko.FitnessApp.user.model.User;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "workouts")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<WorkoutExercise> exercises = new HashSet<>();
}
