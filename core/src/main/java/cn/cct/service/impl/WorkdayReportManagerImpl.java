package cn.cct.service.impl;

import cn.cct.dao.UserDao;
import cn.cct.dao.WorkdayReportDao;
import cn.cct.model.User;
import cn.cct.model.WorkdayReport;
import cn.cct.service.WorkdayReportManager;
import cn.cct.service.impl.GenericManagerImpl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.jws.WebService;

@Service("workdayReportManager")
@WebService(serviceName = "WorkdayReportService", endpointInterface = "cn.cct.service.WorkdayReportManager")
public class WorkdayReportManagerImpl extends GenericManagerImpl<WorkdayReport, Long> implements WorkdayReportManager {
    WorkdayReportDao workdayReportDao;

    @Autowired
    UserDao userDao;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public WorkdayReportManagerImpl(WorkdayReportDao workdayReportDao) {
        super(workdayReportDao);
        this.workdayReportDao = workdayReportDao;
    }


    @Override
    public void exportReports(String storePath) {
        try{
            log.info("Start export reports----------------");



            String today = sdf.format(new Date());
            HashMap<String, Object> queryParams = new HashMap<String, Object>();
            Date queryToday = sdf2.parse(today + " 00:00:00");
            queryParams.put("writeTime", queryToday);
            List workdayReports = workdayReportDao.findByNamedQuery("queryReportsByDate", queryParams);
            log.debug(workdayReports);


            FileInputStream excelInputFile = new FileInputStream(storePath + "workbook.xls");

            /*由文件新增工作簿*/
            Workbook wb = new HSSFWorkbook(excelInputFile);

            /*获取工作表*/
            Sheet sheet = wb.getSheetAt(0);
            int i = 5;
            Iterator<WorkdayReport> iterator = workdayReports.iterator();
            while(iterator.hasNext()){
                log.debug("Row number:" + i);

                WorkdayReport workdayReport = iterator.next();
                User user = userDao.get(workdayReport.getUserId());
                /*获取单元格*/
                Row row = sheet.getRow(i);
                if(row == null){
                    row = sheet.createRow(i);
                }
                Cell cellName = row.getCell(0);
                if(cellName == null){
                    cellName = row.createCell(0);
                }
                Cell cellTodayReport = row.getCell(1);
                if(cellTodayReport == null){
                    cellTodayReport = row.createCell(1);
                }
                Cell cellTomorrowPlan = row.getCell(2);
                if(cellTomorrowPlan == null){
                    cellTomorrowPlan = row.createCell(2);
                }

                /*新建样式*/
                CellStyle cellStyle = wb.createCellStyle();
                cellStyle.setWrapText(true);
                cellStyle.setBorderTop(CellStyle.BORDER_THIN);
                cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
                cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
                cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
                cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
                cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
                cellStyle.setBorderRight(CellStyle.BORDER_THIN);
                cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

                /*垂直居中,水平居左*/
                cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
                cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

                CellStyle cellStyleWriteDate = wb.createCellStyle();
                cellStyleWriteDate.cloneStyleFrom(cellStyle);
                cellStyleWriteDate.setAlignment(CellStyle.ALIGN_CENTER);
                Row rowWirteDate = sheet.getRow(3);
                Cell cellWriteDate = rowWirteDate.getCell(0);
                cellWriteDate.setCellStyle(cellStyleWriteDate);
                cellWriteDate.setCellValue("填报日期:" + today );

                cellName.setCellStyle(cellStyle);
                cellTodayReport.setCellStyle(cellStyle);
                cellTomorrowPlan.setCellStyle(cellStyle);

                cellName.setCellValue(user.getFullName());
                cellTodayReport.setCellValue(workdayReport.getTodayReport());
                cellTomorrowPlan.setCellValue(workdayReport.getTomrrowPlan());

                i++;

            }

            FileOutputStream excelOutputFile = new FileOutputStream(storePath + "WorkdayReport-"+today+".xls");
            wb.write(excelOutputFile);
            excelOutputFile.close();

        }catch (Exception e){
            log.debug(e.getMessage());
        }
    }

    @Override
    public List queryReports(Long userId, Date someday) {
        HashMap<String, Object> queryParams = new HashMap<String, Object>();
        if(userId != null && someday != null ){
            queryParams.put("userId", userId);
            queryParams.put("writeTime", someday);
            return workdayReportDao.findByNamedQuery("queryReportsByTwoColumn", queryParams);
        }else if(userId != null){
            queryParams.put("userId", userId);
            return workdayReportDao.findByNamedQuery("queryReportsByUserId", queryParams);
        }else {
            queryParams.put("writeTime", someday);
            return workdayReportDao.findByNamedQuery("queryReportsByDate", queryParams);
        }
    }

    @Override
    public String getEmailBody() {
        String emailBody = "";
        try{
            log.info("Start edit email body----------------");


            String today = sdf.format(new Date());
            HashMap<String, Object> queryParams = new HashMap<String, Object>();
            Date queryToday = sdf2.parse(today + " 00:00:00");
            queryParams.put("writeTime", queryToday);
            List workdayReports = workdayReportDao.findByNamedQuery("queryReportsByDate", queryParams);
            log.debug(workdayReports);

            emailBody += today + "教育开发组日报:\r\n\r\n";
//            emailBody += "人员\t\t\t\t当日处理\t\t\t\t次日计划\r\n";

            Iterator<WorkdayReport> iterator = workdayReports.iterator();
            while(iterator.hasNext()){


                WorkdayReport workdayReport = iterator.next();
                User user = userDao.get(workdayReport.getUserId());


                String[] todayReport= workdayReport.getTodayReport().split("\r\n");
                log.debug(todayReport.toString());
                String[] tomorrowPlan = workdayReport.getTomrrowPlan().split("\r\n");
                log.debug(tomorrowPlan.toString());
                int reportNum = todayReport.length;
                int planNum = tomorrowPlan.length;
                log.debug(todayReport.length + " "+ tomorrowPlan.length);

                emailBody += user.getFullName() + "\r\n";
                emailBody += "当日处理:";

                for(int i=0; i<reportNum; i++){
                    emailBody += todayReport[i] +";";
                }
                emailBody += "\r\n";
                emailBody += "次日计划:";

                for(int i=0; i<planNum; i++){
                    emailBody += tomorrowPlan[i] +";";
                }
                emailBody += "\r\n\r\n";
            }
        }catch (Exception e){
            log.debug(e.getMessage());
        }finally {
            return emailBody;
        }
    }
}