package osk.sko.FitnessApp.user.model;

import jakarta.persistence.*;
import lombok.*;

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

    public User(String email, String name, String lastName, String phoneNumber, String password, String authority) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.authority = authority;
    }
}
