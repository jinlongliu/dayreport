package cn.cct.webapp.action;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by admin on 15/12/17.
 */
public class ExcelPOITest {
    public static void main(String[] args){
        try{
/*            Workbook wb = new HSSFWorkbook();

            Sheet sheet1 = wb.createSheet("sheet1");


            Row row = sheet1.createRow(0);
            Cell cell1 = row.createCell(0);
//            Cell cell2 = row.createCell(1);

            cell1.setCellType(Cell.CELL_TYPE_STRING);
//            cell2.setCellType(Cell.CELL_TYPE_STRING);

            cell1.setCellValue("管理员");
//            cell2.setCellValue("啥事没做");

            Sheet sheet2 = wb.createSheet("工作表2");

            sheet1.addMergedRegion(new CellRangeAddress(0, 4, 0, 0));




            FileOutputStream excelOutputFile = new FileOutputStream("workbook.xls");
            wb.write(excelOutputFile);
            excelOutputFile.close();*/

            FileInputStream excelInputFile = new FileInputStream("workbook.xls");
            Workbook wb = new HSSFWorkbook(excelInputFile);

            Sheet sheet = wb.getSheetAt(0);
            Row row = sheet.getRow(0);
            if(row == null){
                row= sheet.createRow(0);
            }
            Cell cell = row.getCell(1);
            if( cell == null){
                cell = row.createCell(1);
            }

            Row row2 = sheet.getRow(1);
            if(row2 == null){
                row2= sheet.createRow(1);
            }
            Cell cell2 = row2.getCell(2);
            if( cell2 == null){
                cell2 = row2.createCell(2);
            }

            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setWrapText(true);

            cell.setCellStyle(cellStyle);
            cell2.setCellStyle(cellStyle);

//            cell.setCellType(Cell.CELL_TYPE_STRING);

            cell.setCellValue("1.处理作业\r\n2.改变作业\r\n3.布置作业");
            cell2.setCellValue("1.处理作业\n" +
                    "2.改变作业\n" +
                    "3.布置作业");
            FileOutputStream excelOutputFile = new FileOutputStream("workbook-out.xls");
            wb.write(excelOutputFile);
            excelOutputFile.close();



        }catch (Exception e){

        }
    }
}
