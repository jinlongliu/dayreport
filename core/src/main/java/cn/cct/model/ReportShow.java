package cn.cct.model;

import java.io.Serializable;

/**
 * Created by admin on 15/12/17.
 */
public class ReportShow implements Serializable {
    private WorkdayReport workdayReport;
    private String reportWriteName;

    public WorkdayReport getWorkdayReport() {
        return workdayReport;
    }

    public void setWorkdayReport(WorkdayReport workdayReport) {
        this.workdayReport = workdayReport;
    }

    public String getReportWriteName() {
        return reportWriteName;
    }

    public void setReportWriteName(String reportWriteName) {
        this.reportWriteName = reportWriteName;
    }

    @Override
    public String toString() {
        return "ReportShow{" +
                "workdayReport=" + workdayReport +
                ", reportWriteName='" + reportWriteName + '\'' +
                '}';
    }
}
