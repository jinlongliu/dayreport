package cn.cct.service;

import cn.cct.service.GenericManager;
import cn.cct.model.WorkdayReport;

import java.util.Date;
import java.util.List;
import javax.jws.WebService;

@WebService
public interface WorkdayReportManager extends GenericManager<WorkdayReport, Long> {

    public void exportReports(String storePath);

    public String getEmailBody();

    public List queryReports(Long userId, Date someday);
    
}