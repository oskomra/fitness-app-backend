package osk.sko.FitnessApp.user.model;

import jakarta.persistence.*;
import lombok.*;

@Data
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
    private String phone;
    private String password;
    private String authority;

    public User(String email, String name, String lastName, String phone, String password, String authority) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.password = password;
        this.authority = authority;
    }
}
