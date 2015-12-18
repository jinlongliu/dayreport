package cn.cct.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 15/12/18.
 */
//@Configuration
//@EnableScheduling
@Component("taskJob")
public class TaskJob {

    private static final Logger LOG = LoggerFactory.getLogger(TaskJob.class);

//    @Scheduled(cron = "0/10 * * * * ?")
    public void taskTest(){
        LOG.debug("Job execute one time......");
    }
}
