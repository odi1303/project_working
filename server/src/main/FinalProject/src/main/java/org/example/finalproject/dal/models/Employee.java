import jakarta.persistence.*;
import lombok.Data;
import org.example.finalproject.dal.UserType;

import java.util.List;


@Entity
@DiscriminatorValue("Employee")
@Data
public class Employee extends User {
    @Enumerated(EnumType.STRING)
    @Column(name="type", nullable=false)
    private UserType type;
}