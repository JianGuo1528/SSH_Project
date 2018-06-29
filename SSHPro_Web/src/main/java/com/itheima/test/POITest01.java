package com.itheima.test;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.*;

public class POITest01 {

    @Test
    public void testPOI() throws IOException {
        //1.创建工作簿
        Workbook wb = new XSSFWorkbook();

        //2.创建工作表sheet
        Sheet sheet = wb.createSheet();

        //3.创建行对象
        Row row = sheet.createRow(3);

        //4.创建单元格对象
        Cell cell = row.createCell(3);

        //5.设置单元格内容
        cell.setCellValue("abc");

        //6.设置单元格样式
        CellStyle cellStyle = wb.createCellStyle();

        Font font = wb.createFont();//创建字体
        font.setFontName("Calibri (Body)");
        font.setFontHeightInPoints((short) 12);

        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);

        //7.保存, 关闭流
        OutputStream os = new FileOutputStream("/Users/jianguo/Desktop/test.xlsx");
        wb.write(os);

        os.close();
    }
}
