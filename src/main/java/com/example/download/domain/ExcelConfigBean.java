package com.example.download.domain;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Project jspdemo
 * @ClassName ExcelConfig
 * @Author MPF
 * @Date 2020/5/25 0025 0:50
 * @Version 1.0
 * @Description TODO
 **/

@Data
public class ExcelConfigBean<E> {

    //用于获取class对象
    private String classPath;

    //list中的对象需要放在excel中的属性
    private String[] objAttribute;

    //生成excel文件路径
    private String excelPath;

    //标题
    private String excelTitle;

    //sheet名字
    private String excelSheetName;

    //表头
    private String[] excelHeader;

    //表格列宽
    private Integer[] excelColumnWidth;

    //数据
    private List<E> list;



}
