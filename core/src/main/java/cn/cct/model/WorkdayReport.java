package cn.cct.model;

import cn.cct.model.BaseObject;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import java.util.Date;
import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.GeneratedValue;

import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@Entity
@Table(name="workday_report",catalog="dayreport")
@Indexed
@XmlRootElement
@NamedQueries({
        @NamedQuery(
                name = "queryReportsByDate",
                query = "select wr from WorkdayReport wr where DAY(wr.writeTime) = DAY(:writeTime) "
        ),
        @NamedQuery(
                name = "queryReportsByUserId",
                query = "select wr from WorkdayReport wr where wr.userId = :userId"
        ),
        @NamedQuery(
                name = "queryReportsByTwoColumn",
                query = "select wr from WorkdayReport wr where wr.userId = :userId and DAY(wr.writeTime) = DAY(:writeTime)"
        )
})
public class WorkdayReport extends BaseObject implements Serializable {
    private Long id;
    private Date lastUpdateTime;
    private String todayReport;
    private String tomrrowPlan;
    private Date writeTime;
    private Long userId;

    @Id @GeneratedValue(strategy=IDENTITY) @DocumentId    
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    
    @Column(name="user_id", nullable=false)
    @Field
    public Long getUserId() {
        return this.userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkdayReport pojo = (WorkdayReport) o;

        if (lastUpdateTime != null ? !lastUpdateTime.equals(pojo.lastUpdateTime) : pojo.lastUpdateTime != null) return false;
        if (todayReport != null ? !todayReport.equals(pojo.todayReport) : pojo.todayReport != null) return false;
        if (tomrrowPlan != null ? !tomrrowPlan.equals(pojo.tomrrowPlan) : pojo.tomrrowPlan != null) return false;
        if (writeTime != null ? !writeTime.equals(pojo.writeTime) : pojo.writeTime != null) return false;
        if (userId != null ? !userId.equals(pojo.userId) : pojo.userId != null) return false;

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (lastUpdateTime != null ? lastUpdateTime.hashCode() : 0);
        result = 31 * result + (todayReport != null ? todayReport.hashCode() : 0);
        result = 31 * result + (tomrrowPlan != null ? tomrrowPlan.hashCode() : 0);
        result = 31 * result + (writeTime != null ? writeTime.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(getClass().getSimpleName());

        sb.append(" [");
        sb.append("id").append("='").append(getId()).append("', ");
        sb.append("lastUpdateTime").append("='").append(getLastUpdateTime()).append("', ");
        sb.append("todayReport").append("='").append(getTodayReport()).append("', ");
        sb.append("tomrrowPlan").append("='").append(getTomrrowPlan()).append("', ");
        sb.append("writeTime").append("='").append(getWriteTime()).append("', ");
        sb.append("userId").append("='").append(getUserId()).append("'");
        sb.append("]");
      
        return sb.toString();
    }

}
