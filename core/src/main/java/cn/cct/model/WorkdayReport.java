package cn.cct.model;

import cn.cct.model.BaseObject;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="workday_report",catalog="dayreport")
@Indexed
@XmlRootElement
public class WorkdayReport extends BaseObject implements Serializable {
    private Long id;
    private User reportUser;
    private Date lastUpdateTime;
    private String todayReport;
    private String tomrrowPlan;
    private Date writeTime;

    @Id  @GeneratedValue(strategy=IDENTITY) @DocumentId
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    public User getReportUser() {
        return reportUser;
    }

    public void setReportUser(User reportUser) {
        this.reportUser = reportUser;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="last_update_time", length=19)
    @Field
    public Date getLastUpdateTime() {
        return this.lastUpdateTime;
    }
    
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
    
    @Column(name="today_report")
    @Field
    public String getTodayReport() {
        return this.todayReport;
    }
    
    public void setTodayReport(String todayReport) {
        this.todayReport = todayReport;
    }
    
    @Column(name="tomrrow_plan")
    @Field
    public String getTomrrowPlan() {
        return this.tomrrowPlan;
    }
    
    public void setTomrrowPlan(String tomrrowPlan) {
        this.tomrrowPlan = tomrrowPlan;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="write_time", length=19)
    @Field
    public Date getWriteTime() {
        return this.writeTime;
    }
    
    public void setWriteTime(Date writeTime) {
        this.writeTime = writeTime;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkdayReport pojo = (WorkdayReport) o;

        if (reportUser != null ? !reportUser.equals(pojo.reportUser) : pojo.reportUser != null) return false;
        if (lastUpdateTime != null ? !lastUpdateTime.equals(pojo.lastUpdateTime) : pojo.lastUpdateTime != null) return false;
        if (todayReport != null ? !todayReport.equals(pojo.todayReport) : pojo.todayReport != null) return false;
        if (tomrrowPlan != null ? !tomrrowPlan.equals(pojo.tomrrowPlan) : pojo.tomrrowPlan != null) return false;
        if (writeTime != null ? !writeTime.equals(pojo.writeTime) : pojo.writeTime != null) return false;

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (reportUser != null ? reportUser.hashCode() : 0);
        result = 31 * result + (lastUpdateTime != null ? lastUpdateTime.hashCode() : 0);
        result = 31 * result + (todayReport != null ? todayReport.hashCode() : 0);
        result = 31 * result + (tomrrowPlan != null ? tomrrowPlan.hashCode() : 0);
        result = 31 * result + (writeTime != null ? writeTime.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(getClass().getSimpleName());

        sb.append(" [");
        sb.append("id").append("='").append(getId()).append("', ");
        sb.append("appUser").append("='").append(getReportUser()).append("', ");
        sb.append("lastUpdateTime").append("='").append(getLastUpdateTime()).append("', ");
        sb.append("todayReport").append("='").append(getTodayReport()).append("', ");
        sb.append("tomrrowPlan").append("='").append(getTomrrowPlan()).append("', ");
        sb.append("writeTime").append("='").append(getWriteTime()).append("'");
        sb.append("]");
      
        return sb.toString();
    }

}
