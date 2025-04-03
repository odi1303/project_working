package org.example.finalproject.dal.models.complains;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.finalproject.dal.models.User;

import java.util.Date;
//האם צריך שכל אחת מהמחלקות היורשות יהיו טבלה בפני עצמה? או שכולם תחת המחלקה הזאת? צריך לחשוב על זה ולהחליט
// לדעתי צריך להוסיף לפה @etinity אבל זה תלוי בתכנון
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="origin", discriminatorType = DiscriminatorType.STRING)
@Data()
@NoArgsConstructor()
public abstract class Complain
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="description", nullable = false)
    private String description;

    @Column(name="registered_at", nullable = false)
    private Date registeredAt;

    @Column(name="answered_at")
    private Date answeredAt;

    @Column(name = "compensation")
    private Long compensation;

    @ManyToOne()
    private User complainer;

    public Complain(String description, Date registeredAt, User complainer) {
        this.description = description;
        this.registeredAt = registeredAt;
        this.complainer = complainer;
    }
}

