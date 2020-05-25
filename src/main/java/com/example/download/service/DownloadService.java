package com.example.download.service;

import com.example.download.dao.DownloadMapper;
import com.example.download.domain.ExcelConfigBean;
import com.example.download.domain.Laagent;
import com.example.download.utils.ExcelUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Project jspdemo
 * @ClassName DownloadService
 * @Author MPF
 * @Date 2020/5/23 0023 20:28
 * @Version 1.0
 * @Description TODO
 **/

@Service
public class DownloadService {

    @Autowired
    private DownloadMapper downloadMapper;

    public Integer findDataCount(String keyWords) {
        return downloadMapper.findDataCount(keyWords);
    }

    /**
     * 根据关键词分页查询数据
     *
     * @param keyWords    关键词
     * @param currentPage 当前页
     * @param pageSize    每页显示条数
     * @return
     */
    public PageInfo<Laagent> findDataByPage(String keyWords, String currentPage, String pageSize) {
        PageHelper.startPage(Integer.parseInt(currentPage), Integer.parseInt(pageSize));
        List<Laagent> list = downloadMapper.findDataByPage(keyWords);
        PageInfo<Laagent> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 据关键字查表生成EXCEL
     *
     * @param keyWords 关键字
     */
    public List<Laagent> findDataByKeyWords(String keyWords) {
        //设置固定大小
        Integer defaultCount = 1000;
        Integer currentPage = 1;
        //查询总条数
        Integer dataCount = downloadMapper.findDataCount(keyWords);
        //如果数据大于一千条需要分页查询
        int i = 1;
        int j = 0;
        if (dataCount > defaultCount) {
            i = dataCount / defaultCount;
            j = dataCount % defaultCount;
            if (j != 0) {
                i++;
            }
        }
        //分页查询
        List<Laagent> list = new ArrayList<>();
        for (int k = 1; k <= i; k++) {
            PageHelper.startPage(k, defaultCount);
            List<Laagent> laagents = downloadMapper.findDataByPage(keyWords);
            list.addAll(laagents);
        }
        return list;
    }


    /**
     * 生成EXCEL文件  将生成的文件路径返回
     * @param data
     * @return
     */
    public String makeExcel(String path, List<Laagent> data) throws Exception{
        //excel配置
        ExcelConfigBean excelConfigBean = new ExcelConfigBean();
        excelConfigBean.setClassPath("com.example.download.domain.Laagent");
        excelConfigBean.setObjAttribute(new String[]{"num", "agentcode", "devno1", "devno2", "name", "managecom", "password", "trace", "gender", "marry", "nation", "station", "detail"});
        excelConfigBean.setExcelPath(path + "//测试表单.xlsx");
        excelConfigBean.setExcelSheetName("测试表单");
        excelConfigBean.setExcelTitle("测试表单");
        excelConfigBean.setExcelHeader(new String[]{"序号", "工号", "分公司", "支公司", "姓名", "公司内码", "密码", "轨迹", "性别", "婚姻状况", "民族", "区站", "描述"});
        excelConfigBean.setExcelColumnWidth(new Integer[]{50, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100});
        excelConfigBean.setList(data);
        //生成Excel文件
        ExcelUtil.createExcel(excelConfigBean);
        return excelConfigBean.getExcelPath();
    }


}
