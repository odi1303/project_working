package il.cshaifasweng.OCSFMediatorExample.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class complaint_to_answer {
    private String email;
    private String headline;
    private String description;
    private String branch;
    private Date registeredAt;  // Added date field

    public complaint_to_answer(String email, String headline, String description, String branch) {
        this.email = email;
        this.headline = headline;
        this.description = description;
        this.branch = branch;
        this.registeredAt = new Date();  // Default to current date
    }

    // Constructor used by server when converting from Complain
    public complaint_to_answer(String email, String headline, String description, String branch, Date registeredAt) {
        this.email = email;
        this.headline = headline;
        this.description = description;
        this.branch = branch;
        this.registeredAt = registeredAt;
    }

    public String getEmail() { return email; }
    public String getHeadline() { return headline; }
    public String getDescription() { return description; }
    public String getBranch() { return branch; }
    public String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(registeredAt);
    }

    // Additional getters if needed
    public Date getRegisteredAt() { return registeredAt; }
}