package osk.sko.FitnessApp.exercise.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "body_parts")
public class BodyPart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;

    public BodyPart(String name) {
        this.name = name;
    }
}
