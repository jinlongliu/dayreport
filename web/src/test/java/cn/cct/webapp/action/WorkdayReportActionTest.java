package cn.cct.webapp.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import cn.cct.service.WorkdayReportManager;
import cn.cct.model.WorkdayReport;
import cn.cct.webapp.action.BaseActionTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class WorkdayReportActionTest extends BaseActionTestCase {
    private WorkdayReportAction action;

    @Autowired
    private WorkdayReportManager workdayReportManager;

    @Before
    public void onSetUp() {
        super.onSetUp();

        action = new WorkdayReportAction();
        action.setWorkdayReportManager(workdayReportManager);

        // add a test workdayReport to the database
        WorkdayReport workdayReport = new WorkdayReport();

        // enter all required fields
        workdayReport.setUserId(14320283908028909L);

        workdayReportManager.save(workdayReport);
    }

    @Test
    public void testGetAllWorkdayReports() throws Exception {
        assertEquals(action.list(), ActionSupport.SUCCESS);
        assertTrue(action.getWorkdayReports().size() >= 1);
    }

    @Test
    public void testSearch() throws Exception {
        // regenerate indexes
        workdayReportManager.reindex();

        action.setQ("*");
        assertEquals(action.list(), ActionSupport.SUCCESS);
        assertEquals(4, action.getWorkdayReports().size());
    }

    @Test
    public void testEdit() throws Exception {
        log.debug("testing edit...");
        action.setId(-1L);
        assertNull(action.getWorkdayReport());
        assertEquals("success", action.edit());
        assertNotNull(action.getWorkdayReport());
        assertFalse(action.hasActionErrors());
    }

    @Test
    public void testSave() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletActionContext.setRequest(request);
        action.setId(-1L);
        assertEquals("success", action.edit());
        assertNotNull(action.getWorkdayReport());

        WorkdayReport workdayReport = action.getWorkdayReport();
        // update required fields
        workdayReport.setUserId(2085512767416499L);

        action.setWorkdayReport(workdayReport);

        assertEquals("input", action.save());
        assertFalse(action.hasActionErrors());
        assertFalse(action.hasFieldErrors());
        assertNotNull(request.getSession().getAttribute("messages"));
    }

    @Test
    public void testRemove() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletActionContext.setRequest(request);
        action.setDelete("");
        WorkdayReport workdayReport = new WorkdayReport();
        workdayReport.setId(-2L);
        action.setWorkdayReport(workdayReport);
        assertEquals("success", action.delete());
        assertNotNull(request.getSession().getAttribute("messages"));
    }


}
