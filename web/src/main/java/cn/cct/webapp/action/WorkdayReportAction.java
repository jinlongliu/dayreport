package cn.cct.webapp.action;

import cn.cct.Constants;
import cn.cct.model.ReportShow;
import cn.cct.model.Role;
import cn.cct.model.User;
import cn.cct.service.UserManager;
import com.opensymphony.xwork2.Preparable;
import cn.cct.service.WorkdayReportManager;
import cn.cct.dao.SearchException;
import cn.cct.model.WorkdayReport;
import cn.cct.webapp.action.BaseAction;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.util.*;


public class WorkdayReportAction extends BaseAction implements Preparable {
    private WorkdayReportManager workdayReportManager;
    private List workdayReports;
    private WorkdayReport workdayReport;
    private Long id;
    private String query;

    private List reportShows;
    private String queryLabel;
    private Date queryDate;


    /*参数是否是管理员*/
    private boolean userIsAdmin = false;

    private Long someoneUserId;

    private List allUsers;

    @Autowired
    private UserManager userManager;

    public void setWorkdayReportManager(WorkdayReportManager workdayReportManager) {
        this.workdayReportManager = workdayReportManager;
    }

    public List getWorkdayReports() {
        return workdayReports;
    }

    public List getReportShows() {
        return reportShows;
    }

    public boolean isUserIsAdmin() {
        return userIsAdmin;
    }

    public Long getSomeoneUserId() {
        return someoneUserId;
    }

    public void setSomeoneUserId(Long someoneUserId) {
        this.someoneUserId = someoneUserId;
    }

    public String getQueryLabel() {
        queryLabel = getText("workdayReport.writeName");
        return queryLabel;
    }

    public Date getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
    }

    public List getAllUsers() {
        allUsers = userManager.getAll();
        return allUsers;
    }

    /**
     * Grab the entity from the database before populating with request parameters
     */
    public void prepare() {
        if (getRequest().getMethod().equalsIgnoreCase("post")) {
            // prevent failures on new
            String workdayReportId = getRequest().getParameter("workdayReport.id");
            if (workdayReportId != null && !workdayReportId.equals("")) {
                workdayReport = workdayReportManager.get(new Long(workdayReportId));
            }
        }
    }


    public void setQ(String q) {
        this.query = q;
    }


    public String showReports(){
        try {
            log.debug("Show reports.......");


            if(someoneUserId == null && queryDate == null){
                workdayReports = workdayReportManager.search(query, WorkdayReport.class);
            }else {
                workdayReports = workdayReportManager.queryReports(someoneUserId, queryDate);
            }

            reportShows = new ArrayList<ReportShow>();
            Iterator<WorkdayReport> it = workdayReports.iterator();
            while (it.hasNext()){
                WorkdayReport workdayReportView = it.next();
                User user =  userManager.get(workdayReportView.getUserId());
                ReportShow reportShow = new ReportShow();
                reportShow.setWorkdayReport(workdayReportView);
                reportShow.setReportWriteName(user.getFullName());
                reportShows.add(reportShow);
            }
        } catch (Exception se) {
            addActionError(se.getMessage());
            log.debug(se.getMessage());
            workdayReports = workdayReportManager.getAll();
        }
        return SUCCESS;
    }


    public String list() {
        try {
            workdayReports = workdayReportManager.search(query, WorkdayReport.class);
           /* Iterator<WorkdayReport> it = workdayReports.iterator();
            while (it.hasNext()){
                WorkdayReport workdayReportView = it.next();
                User user =  userManager.get(workdayReportView.getUserId());
                if(user != null){
                    workdayReportView.setWirteName(user.getFullName());
                }
            }*/
        } catch (SearchException se) {
            addActionError(se.getMessage());
            workdayReports = workdayReportManager.getAll();
        }
        return SUCCESS;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkdayReport getWorkdayReport() {
        return workdayReport;
    }

    public void setWorkdayReport(WorkdayReport workdayReport) {
        this.workdayReport = workdayReport;
    }

    public String delete() {
        Long loginUserId = (Long)getSession().getAttribute("loginUserId");
        User user = userManager.get(loginUserId);
        if(!getRequest().isUserInRole(Constants.ADMIN_ROLE)){
            log.debug("User is not admin,can not delete");
            saveMessage(getText("right.delete.message"));
            return INPUT;
        }
        workdayReportManager.remove(workdayReport.getId());
        saveMessage(getText("workdayReport.deleted"));

        return SUCCESS;
    }

    public String edit() {
        if(getRequest().isUserInRole(Constants.ADMIN_ROLE)){
           userIsAdmin = true;
        }
        if (id != null) {
            workdayReport = workdayReportManager.get(id);
        } else {
            workdayReport = new WorkdayReport();
        }
        return SUCCESS;
    }

    public String save() throws Exception {
        if (cancel != null) {
            return "cancel";
        }

        if (delete != null) {
            return delete();
        }

        Date now = new Date();

        /*通过判断是否有日报ID来判定是新增还是编辑*/
        boolean isNew = (workdayReport.getId() == null);
        Long loginUserId = (Long)getSession().getAttribute("loginUserId");
        if(isNew) {
            log.debug("User id is:" + loginUserId);
            if(loginUserId != null){
                workdayReport.setUserId(loginUserId);
            }
            workdayReport.setWriteTime(now);
            workdayReport.setLastUpdateTime(now);
        }else {
            if(workdayReport.getUserId() != loginUserId){
                log.debug("You have not right to edit this report");
                saveMessage(getText("right.error.message"));
                return INPUT;
            }
            /*如果不是新增是编辑,则更新最后编辑时间*/
            workdayReport.setLastUpdateTime(now);
        }

        workdayReportManager.save(workdayReport);

        String key = (isNew) ? "workdayReport.added" : "workdayReport.updated";
        saveMessage(getText(key));

        if (!isNew) {
            return INPUT;
        } else {
            return SUCCESS;
        }
    }
}