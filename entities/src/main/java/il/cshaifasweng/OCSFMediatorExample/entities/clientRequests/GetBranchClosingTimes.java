package il.cshaifasweng.OCSFMediatorExample.entities.clientRequests;

import java.io.Serializable;
import java.time.LocalDate;

public class GetBranchClosingTimes implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String branchName;
    private final LocalDate date;

    public GetBranchClosingTimes(String branchName, LocalDate date) {
        this.branchName = branchName;
        this.date = date;
    }

    public String getBranchName() {
        return branchName;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "GetBranchClosingTimes{" +
                "branchName='" + branchName + '\'' + ", date=" + date +
                '}';
    }
}
