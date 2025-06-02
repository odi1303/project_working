package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class ClosingTimes implements Serializable {

    private static final long serialVersionUID = 1L;

    private String branchName;
    private String closingTime;

    public ClosingTimes(String branchName, String closingTime) {
        this.branchName = branchName;
        this.closingTime = closingTime;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    @Override
    public String toString() {
        return "ClosingTimes{" +
                "branchName='" + branchName + '\'' +
                ", closingTime='" + closingTime + '\'' +
                '}';
    }
}
