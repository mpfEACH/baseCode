package com.example.download.utils;

import com.example.download.domain.ExcelConfigBean;
import com.example.download.domain.Laagent;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.List;

/**
 * @Project jspdemo
 * @ClassName ExcelUtil
 * @Author MPF
 * @Date 2020/5/24 0024 22:05
 * @Version 1.0
 * @Description TODO
 **/
public class ExcelUtil {

    public static void createExcel(ExcelConfigBean excelConfigBean) throws Exception{

        //创建基于stream的工作薄对象的参数是超过1000条数据就放进硬盘减少内存占用  outputStream用于向EXCEL中输入
        try(SXSSFWorkbook wb = new SXSSFWorkbook(10000); OutputStream outputStream = new FileOutputStream(excelConfigBean.getExcelPath())){
            //获取list集合
            List list = excelConfigBean.getList();
            //设置EXCEL标题样式
            CellStyle cellTitleStyle = setTitleStyle(wb);
            //设置EXCEL表头样式
            CellStyle cellHeaderStyle = setHeaderStyle(wb);
            //设置EXCEL内容样式
            CellStyle cellContentStyle = setContentStyle(wb);

            //创建一个sheet页  加以名称
            SXSSFSheet sheet = wb.createSheet(excelConfigBean.getExcelSheetName());

            //设置列宽  第一个参数是第几列  第二个参数是像素 px
            for (int i = 0; i < list.size(); i++) {
                //i等于0表示序号列
                if(i == 0){
                    sheet.setColumnWidth(i, (short)500 * 5);
                    continue;
                }
                sheet.setColumnWidth(i, (short)1000 * 5);
            }

            //合并单元格  将EXCEL第一行合并成一列  多少列就合并多少  用于写标题  这四个参数是起始行、起始列、结束行、结束列
            String[] excelHeaderArr = excelConfigBean.getExcelHeader();
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, excelHeaderArr.length - 1));

            //创建第一行的对象   其实也就是标题
            SXSSFRow rowTitle = sheet.createRow(0);
            //创建第一行第一列的cell对象
            SXSSFCell cellTitle = rowTitle.createCell(0);
            cellTitle.setCellStyle(cellTitleStyle);
            cellTitle.setCellValue(excelConfigBean.getExcelTitle());

            //创建第二行的对象   写上制表日期
            SXSSFRow row = sheet.createRow(1);
            //创建第一行最后一列
            SXSSFCell cellDate = row.createCell(excelHeaderArr.length - 1);
            cellDate.setCellValue("制表日期：" + LocalDate.now());

            //设置表头  循环存入表头
            SXSSFRow rowHeader = sheet.createRow(2);
            for (int i = 0; i < excelHeaderArr.length; i++) {
                SXSSFCell cellHeader = rowHeader.createCell(i);
                cellHeader.setCellStyle(cellHeaderStyle);
                cellHeader.setCellValue(excelHeaderArr[i]);
            }

            //设置EXCEL内容  将list中的数据放入
            for (int i = 0; i < list.size(); i++) {
                //内容是从第四行开始  第一行：标题  第二行：制表日期   第三行：表头   第四行：内容  从0开始  这里就是从3开始
                SXSSFRow rowContent = sheet.createRow(i + 3);
                //获取反射路径
                Object object = list.get(i);
                String[] objAttribute = excelConfigBean.getObjAttribute();
                for (int j = 0; j < excelHeaderArr.length; j++) {
                    //设置内容单元格样式
                    SXSSFCell cellContent = rowContent.createCell(j);
                    cellContent.setCellStyle(cellContentStyle);
                    // j 等于 0 表示是序号列   序号列就是list集合长度
                    if(j == 0){
                        cellContent.setCellValue(i + 1);
                        continue;
                    }
                    //获取反射对象   通过传递的属性获取属性值
                    Class clazz = object.getClass();
                    //将传递的属性首字母大写
                    String methodName = objAttribute[j].substring(0,1).toUpperCase() + objAttribute[j].substring(1);
                    Method method = clazz.getMethod("get" + methodName);
                    Object attribute = method.invoke(object);
                    //这里是强制转型  可能会有问题
                    cellContent.setCellValue((String) attribute);
                }
            }

            //将内存的excel生成
            wb.write(outputStream);
        }

    }

    /**
     * 设置EXCEL标题样式
     * @param wb
     * @return
     */
    private static CellStyle setTitleStyle(SXSSFWorkbook wb){
        //设置标题样式单元格格式一定要写在for循环外边最多就能6400个样式其实正常开发也用不着几个
        CellStyle cellStyleTitle = wb.createCellStyle();
        //首先设置EXCEL中的标题
        //居中
        cellStyleTitle.setAlignment(HorizontalAlignment.CENTER);
        //字体加粗设置字体大小标题15px够了看着还挺好看  字体加粗   字高15px
        Font fontTitle = wb.createFont();
        fontTitle.setBold(true);
        fontTitle.setFontHeightInPoints((short) 15);
        cellStyleTitle.setFont(fontTitle);
        return cellStyleTitle;
    }


    /**
     * 设置EXCEL表头样式
     * @return
     */
    private static CellStyle setHeaderStyle(SXSSFWorkbook wb){
        //设置表头样式
        CellStyle cellStyleHeader = wb.createCellStyle();
        cellStyleHeader.setAlignment(HorizontalAlignment.CENTER);
        //设置表头字体样式  字体加粗
        Font font = wb.createFont();
        font.setBold(true);
        //放入表头字体样式
        cellStyleHeader.setFont(font);
        //加边框
        cellStyleHeader.setBorderLeft(BorderStyle.THIN);
        cellStyleHeader.setBorderRight(BorderStyle.THIN);
        cellStyleHeader.setBorderTop(BorderStyle.THIN);
        cellStyleHeader.setBorderBottom(BorderStyle.THIN);

        return cellStyleHeader;
    }



    /**
     * 设置EXCEL内容样式
     * @return
     */
    private static CellStyle setContentStyle(SXSSFWorkbook wb){
        //设置内容样式
        CellStyle cellStyleContent = wb.createCellStyle();
        cellStyleContent.setAlignment(HorizontalAlignment.CENTER);
        return cellStyleContent;
    }




}
