import jakarta.persistence.*;
import lombok.Data;
import org.example.finalproject.dal.UserType;

import java.util.List;


@Entity
@DiscriminatorValue("BranchManger")
@Data
public class Employee extends Employee {
    private int branchId;
}