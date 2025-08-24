package osk.sko.FitnessApp.user.model;

import jakarta.persistence.*;
import lombok.*;
import osk.sko.FitnessApp.workout.model.Workout;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String password;
    private String authority;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Workout> workouts = new ArrayList<>();

    public User(String email, String name, String lastName, String phoneNumber, String password, String authority) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.authority = authority;
    }
}
