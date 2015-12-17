package cn.cct.dao;

import cn.cct.dao.BaseDaoTestCase;
import cn.cct.model.WorkdayReport;
import org.springframework.dao.DataAccessException;

import static org.junit.Assert.*;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class WorkdayReportDaoTest extends BaseDaoTestCase {
    @Autowired
    private WorkdayReportDao workdayReportDao;

    @Test(expected=DataAccessException.class)
    public void testAddAndRemoveWorkdayReport() {
        WorkdayReport workdayReport = new WorkdayReport();

        // enter all required fields
        workdayReport.setUserId(1000L);

        log.debug("adding workdayReport...");
        workdayReport = workdayReportDao.save(workdayReport);

        workdayReport = workdayReportDao.get(workdayReport.getId());

        assertNotNull(workdayReport.getId());

        log.debug("removing workdayReport...");

        workdayReportDao.remove(workdayReport.getId());

        // should throw DataAccessException 
        workdayReportDao.get(workdayReport.getId());
    }
}