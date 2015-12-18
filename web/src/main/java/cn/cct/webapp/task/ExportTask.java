package cn.cct.webapp.task;

import cn.cct.service.MailEngine;
import cn.cct.service.WorkdayReportManager;
import cn.cct.webapp.action.BaseAction;
import com.opensymphony.xwork2.TextProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by admin on 15/12/18.
 */
@Component("exportTask")
public class ExportTask implements ApplicationContextAware {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

    private static final Logger LOG = LoggerFactory.getLogger(ExportTask.class);

    @Autowired
    private WorkdayReportManager workdayReportManager;

    @Autowired
    private MailEngine mailEngine;

    private static ApplicationContext applicationContext;
    private static WebApplicationContext webApplicationContext;

    private String now;
    private String today;


    //    @Scheduled(cron = "0/10 * * * * ?")
    public void exportReports(){
        now = sdf.format(new Date());
        LOG.debug("Export job start at time:" + now);
        LOG.debug(workdayReportManager.toString());
        webApplicationContext = ContextLoader.getCurrentWebApplicationContext();

        String contextPath = webApplicationContext.getServletContext().getContextPath();
        String realPath = webApplicationContext.getServletContext().getRealPath("/workreport");
        String dictoryPath = realPath + "/";
        LOG.debug(contextPath);
        LOG.debug(realPath);
        workdayReportManager.exportReports(dictoryPath);
        String emailBody = workdayReportManager.getEmailBody();

        today = sdf2.format(new Date());
        final String attachmentFile = dictoryPath + "WorkdayReport-"+today+".xls";


        sendEmails(attachmentFile, emailBody);

    }

    public void sendEmails(String attachmentFile, String emailBody){
        try{
            final String ATTACHMENT_NAME = "WorkdayReport"+today+".xls";

            String emailSubject = "华讯教育开发组"+today +"日报";
            String emailFrom = "华讯教育开发组 <huaxunchina_jira@126.com>";

            String fileName = "workdayreport-temp.xls";
            String classPath = this.getClass().getResource("").toString().split(":")[1];
            File in = new File(attachmentFile);
            File out = new File(classPath + fileName);
            FileCopyUtils.copy(in, out);
            ClassPathResource cpResource = new ClassPathResource(fileName, ExportTask.class);

            LOG.debug(attachmentFile);

            mailEngine.sendMessage(new String[] {
                    "nnuljl@qq.com"
            }, emailFrom, cpResource, emailBody, emailSubject, ATTACHMENT_NAME);

        }catch (Exception e){
            LOG.debug(e.getMessage());
        }


    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
