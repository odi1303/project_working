package il.cshaifasweng.OCSFMediatorExample.server.dal.models;
import jakarta.persistence.*;
import lombok.Data;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.User;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "complaints" )
public class Complaint implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "Branch")
    String branch;
    //@ManyToOne
    @Column(name="User")
    User user;
    @Column
    String description;
    @Column
    String email;
    @Column
    String headline;
    @Column
    Date date;
    @Column
    boolean handled = false;
    @Column
    int compensation=0;
    @Column
    Date answerdAt=null;

    public Complaint(String branch, String headline, String description,Date date, String email) {
        this.branch = branch;
        this.description = description;
        this.email = email;
        this.headline = headline;
        this.handled = false;
        this.date = date;
    }

    public Complaint() {

    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isHandled() {
        return handled;
    }

    public void setHandled(boolean handled) {
        this.handled = handled;
    }

    public int getCompensation() {
        return compensation;
    }

    public void setCompensation(int compensation) {
        this.compensation = compensation;
    }

    public Long getId() {
        return id;
    }

    public void setAnsweredAt(Date date) {
        this.answerdAt = date;
    }
    public Date getAnsweredAt() {
        return answerdAt;
    }
}
