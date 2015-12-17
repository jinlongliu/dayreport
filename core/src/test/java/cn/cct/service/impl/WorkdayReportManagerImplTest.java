package cn.cct.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.cct.dao.WorkdayReportDao;
import cn.cct.model.WorkdayReport;
import cn.cct.service.impl.BaseManagerMockTestCase;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

public class WorkdayReportManagerImplTest extends BaseManagerMockTestCase {

    @InjectMocks
    private WorkdayReportManagerImpl manager;

    @Mock
    private WorkdayReportDao dao;

    @Test
    public void testGetWorkdayReport() {
        log.debug("testing get...");
        //given
        final Long id = 7L;
        final WorkdayReport workdayReport = new WorkdayReport();
        given(dao.get(id)).willReturn(workdayReport);

        //when
        WorkdayReport result = manager.get(id);

        //then
        assertSame(workdayReport, result);
    }

    @Test
    public void testGetWorkdayReports() {
        log.debug("testing getAll...");
        //given
        final List<WorkdayReport> workdayReports = new ArrayList<>();
        given(dao.getAll()).willReturn(workdayReports);

        //when
        List result = manager.getAll();

        //then
        assertSame(workdayReports, result);
    }

    @Test
    public void testSaveWorkdayReport() {
        log.debug("testing save...");

        //given
        final WorkdayReport workdayReport = new WorkdayReport();
        // enter all required fields
        workdayReport.setUserId(1000L);

        given(dao.save(workdayReport)).willReturn(workdayReport);

        //when
        manager.save(workdayReport);

        //then
        verify(dao).save(workdayReport);
    }

    @Test
    public void testRemoveWorkdayReport() {
        log.debug("testing remove...");

        //given
        final Long id = -11L;
        willDoNothing().given(dao).remove(id);

        //when
        manager.remove(id);

        //then
        verify(dao).remove(id);
    }
}
