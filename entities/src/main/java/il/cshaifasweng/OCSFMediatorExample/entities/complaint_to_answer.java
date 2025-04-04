package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "Complaints_to_answer")
public class complaint_to_answer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int indentifier;
    @OneToMany
    //User user;
    String email; //the email to respond to
    String headline; //short description of the complaint
    String body;// the full complaint
    String branch;// the specific branch
    Date date;// the date the complaint has been filed in
    public complaint_to_answer(String email, String headline, String body, String branch, Date date) {
        this.email = email;
        this.headline = headline;
        this.body = body;
        this.branch = branch;
        this.date = date;
    }
    public complaint_to_answer(String email, String headline, String body, String branch) {
        this.email = email;
        this.headline = headline;
        this.body = body;
        this.branch = branch;
        this.date= new Date();
    }
    public complaint_to_answer(User user,String email, String headline, String body, String branch, Date date) {
        //this.user = user;
        this.email=email;
        this.headline = headline;
        this.body = body;
        this.branch = branch;
        this.date = date;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
