package il.cshaifasweng.OCSFMediatorExample.server.dal.models.complains;
import jakarta.persistence.*;
import lombok.Data;
import il.cshaifasweng.OCSFMediatorExample.entities.models.User;

import java.io.Serializable;
import java.util.Date;
//האם צריך שכל אחת מהמחלקות היורשות יהיו טבלה בפני עצמה? או שכולם תחת המחלקה הזאת? צריך לחשוב על זה ולהחליט
// לדעתי צריך להוסיף לפה @etinity אבל זה תלוי בתכנון
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="origin", discriminatorType = DiscriminatorType.STRING)
@Data()
@Entity
@Table(name="complains")
public class/*abstract*/ Complain implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "branch")
    public String branch;

    @Column(name = "headline")
    public String headline;

    public Long branch_id;

    @Column(name="description", nullable = false)
    public String description;

    @Column(name="registered_at", nullable = false)
    public Date registeredAt;

    @Column(name="answered_at")
    public Date answeredAt;

    @Column(name = "compensation")
    public Long compensation;

    /*@ManyToOne()
    public User complainer;*/

    @Column(name = "email")
    public String email;

    public Complain() {

    }

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

    /*public User getComplainer() {
        return complainer;
    }

    public void setComplainer(User complainer) {
        this.complainer = complainer;
    }*/

    public Complain(String description, Date registeredAt, User complainer) {
        this.description = description;
        this.registeredAt = registeredAt;
        //this.complainer = complainer;
    }

    public Complain(String branch, String headline, String description, Date registeredAt, String email) {
        this.branch = branch;
        this.headline = headline;
        this.description = description;
        this.registeredAt = registeredAt;
        this.email = email;
    }
    public Complain(Complain complain){
        this.branch = complain.branch;
        this.headline = complain.headline;
        this.description = complain.description;
        this.registeredAt = complain.registeredAt;
        //this.complainer = complain.complainer;
        this.email = complain.email;
        this.compensation = complain.compensation;
        this.answeredAt = complain.answeredAt;
        this.id = complain.id;
    }
}

