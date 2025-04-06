package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class OpeningTimes implements Serializable {

    private static final long serialVersionUID = 1L;
    private String branchName;
    private String openingTime;

    public OpeningTimes(String branchName, String openingTime) {
        this.branchName = branchName;
        this.openingTime = openingTime;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }
}
