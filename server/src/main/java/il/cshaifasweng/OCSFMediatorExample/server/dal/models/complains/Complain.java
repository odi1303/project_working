package il.cshaifasweng.OCSFMediatorExample.server.dal.models.complains;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.User;

import java.util.Date;
//האם צריך שכל אחת מהמחלקות היורשות יהיו טבלה בפני עצמה? או שכולם תחת המחלקה הזאת? צריך לחשוב על זה ולהחליט
// לדעתי צריך להוסיף לפה @etinity אבל זה תלוי בתכנון
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="origin", discriminatorType = DiscriminatorType.STRING)
@Data()
@NoArgsConstructor()
@Entity
public abstract class Complain
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name="description", nullable = false)
    public String description;

    @Column(name="registered_at", nullable = false)
    public Date registeredAt;

    @Column(name="answered_at")
    public Date answeredAt;

    @Column(name = "compensation")
    public Long compensation;

    @ManyToOne()
    public User complainer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Date registeredAt) {
        this.registeredAt = registeredAt;
    }

    public Date getAnsweredAt() {
        return answeredAt;
    }

    public void setAnsweredAt(Date answeredAt) {
        this.answeredAt = answeredAt;
    }

    public Long getCompensation() {
        return compensation;
    }

    public void setCompensation(Long compensation) {
        this.compensation = compensation;
    }

    public User getComplainer() {
        return complainer;
    }

    public void setComplainer(User complainer) {
        this.complainer = complainer;
    }

    public Complain(String description, Date registeredAt, User complainer) {
        this.description = description;
        this.registeredAt = registeredAt;
        this.complainer = complainer;
    }
}

