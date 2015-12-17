package cn.cct.service.impl;

import cn.cct.dao.WorkdayReportDao;
import cn.cct.model.WorkdayReport;
import cn.cct.service.WorkdayReportManager;
import cn.cct.service.impl.GenericManagerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.jws.WebService;

@Service("workdayReportManager")
@WebService(serviceName = "WorkdayReportService", endpointInterface = "cn.cct.service.WorkdayReportManager")
public class WorkdayReportManagerImpl extends GenericManagerImpl<WorkdayReport, Long> implements WorkdayReportManager {
    WorkdayReportDao workdayReportDao;

    @Autowired
    public WorkdayReportManagerImpl(WorkdayReportDao workdayReportDao) {
        super(workdayReportDao);
        this.workdayReportDao = workdayReportDao;
    }
}