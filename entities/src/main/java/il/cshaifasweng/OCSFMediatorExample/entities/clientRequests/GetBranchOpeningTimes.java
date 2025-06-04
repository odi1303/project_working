package il.cshaifasweng.OCSFMediatorExample.entities.clientRequests;

import java.io.Serializable;
import java.time.LocalDate;

public class GetBranchOpeningTimes implements Serializable {

    private final String branchName;
    private final LocalDate Date;

    public GetBranchOpeningTimes(String branchName, LocalDate Date) {
        this.branchName = branchName;
        this.Date = Date;
    }

    public String getBranchName() {
        return branchName;
    }
    public LocalDate getDate() {
        return Date;
    }

    @Override
    public String toString() {
        return "GetBranchOpeningTimes{" +
                "branchName='" + branchName + '\'' + ", Date=" + Date +
                '}';
    }
}
