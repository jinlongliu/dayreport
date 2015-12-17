package cn.cct.dao.hibernate;

import cn.cct.model.WorkdayReport;
import cn.cct.dao.WorkdayReportDao;
import cn.cct.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

@Repository("workdayReportDao")
public class WorkdayReportDaoHibernate extends GenericDaoHibernate<WorkdayReport, Long> implements WorkdayReportDao {

    public WorkdayReportDaoHibernate() {
        super(WorkdayReport.class);
    }
}
