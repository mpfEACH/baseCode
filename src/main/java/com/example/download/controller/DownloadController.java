package com.example.download.controller;

import com.example.download.domain.Laagent;
import com.example.download.domain.LayuiTableBean;
import com.example.download.service.DownloadService;
import com.example.download.utils.DownloadUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Project jspdemo
 * @ClassName DownloadController
 * @Author MPF
 * @Date 2020/5/23 0023 20:29
 * @Version 1.0
 * @Description TODO
 **/

@RestController
@RequestMapping("/download")
public class DownloadController {

    @Autowired
    private DownloadService downloadService;


    /**
     * 根据关键词分页查询数据
     * @param keyWords 关键词
     * @param currentPage 当前页
     * @param pageSize 每页显示条数
     * @return
     */
    @RequestMapping("/findDataByPage/{keyWords}/{currentPage}/{pageSize}")
    public LayuiTableBean findDataByPage(@PathVariable("keyWords")String keyWords, @PathVariable("currentPage")String currentPage, @PathVariable("pageSize")String pageSize){
        Integer dataCount = downloadService.findDataCount(keyWords);
        PageInfo<Laagent> pageInfo = downloadService.findDataByPage(keyWords, currentPage, pageSize);
        LayuiTableBean layuiTableBean = new LayuiTableBean();
        layuiTableBean.setCode("0");
        layuiTableBean.setCount(dataCount.toString());
        layuiTableBean.setData(pageInfo.getList());
        return layuiTableBean;
    }

    /**
     * 根据关键字查表生成EXCEL
     * @param request 请求
     * @param response 响应
     * @param keyWords 关键字
     */
    @RequestMapping("/excel/{keyWords}")
    public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable("keyWords") String keyWords) throws Exception{
        //获取项目路径
        String realPath = request.getSession().getServletContext().getRealPath("/excel");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        System.out.println("excel文件地址：" + realPath);
        //先查询数据
        List<Laagent> data = downloadService.findDataByKeyWords(keyWords);
        System.out.println("数据查询完毕！");
        //将查询的集合生成EXCEL  在 realPath 下面生成
        String path = downloadService.makeExcel(realPath, data);
        System.out.println("生成报表完毕！");
        //将生成的Excel返回到浏览器
        DownloadUtil.download(response, "测试表单.xlsx",path );
        System.out.println("下载报表完毕！");
    }


    @RequestMapping("/txt")
    public String downloadTXT(){
        return null;
    }


    @RequestMapping("/test")
    public String ss(){
        String json = "{\"code\":0,\"msg\":\"\",\"count\":1000,\"data\":[{\"id\":10000,\"username\":\"user-0\",\"sex\":\"女\",\"city\":\"城市-0\",\"sign\":\"签名-0\",\"experience\":255,\"logins\":24,\"wealth\":82830700,\"classify\":\"作家\",\"score\":57},{\"id\":10001,\"username\":\"user-1\",\"sex\":\"男\",\"city\":\"城市-1\",\"sign\":\"签名-1\",\"experience\":884,\"logins\":58,\"wealth\":64928690,\"classify\":\"词人\",\"score\":27},{\"id\":10002,\"username\":\"user-2\",\"sex\":\"女\",\"city\":\"城市-2\",\"sign\":\"签名-2\",\"experience\":650,\"logins\":77,\"wealth\":6298078,\"classify\":\"酱油\",\"score\":31},{\"id\":10003,\"username\":\"user-3\",\"sex\":\"女\",\"city\":\"城市-3\",\"sign\":\"签名-3\",\"experience\":362,\"logins\":157,\"wealth\":37117017,\"classify\":\"诗人\",\"score\":68},{\"id\":10004,\"username\":\"user-4\",\"sex\":\"男\",\"city\":\"城市-4\",\"sign\":\"签名-4\",\"experience\":807,\"logins\":51,\"wealth\":76263262,\"classify\":\"作家\",\"score\":6},{\"id\":10005,\"username\":\"user-5\",\"sex\":\"女\",\"city\":\"城市-5\",\"sign\":\"签名-5\",\"experience\":173,\"logins\":68,\"wealth\":60344147,\"classify\":\"作家\",\"score\":87},{\"id\":10006,\"username\":\"user-6\",\"sex\":\"女\",\"city\":\"城市-6\",\"sign\":\"签名-6\",\"experience\":982,\"logins\":37,\"wealth\":57768166,\"classify\":\"作家\",\"score\":34},{\"id\":10007,\"username\":\"user-7\",\"sex\":\"男\",\"city\":\"城市-7\",\"sign\":\"签名-7\",\"experience\":727,\"logins\":150,\"wealth\":82030578,\"classify\":\"作家\",\"score\":28},{\"id\":10008,\"username\":\"user-8\",\"sex\":\"男\",\"city\":\"城市-8\",\"sign\":\"签名-8\",\"experience\":951,\"logins\":133,\"wealth\":16503371,\"classify\":\"词人\",\"score\":14},{\"id\":10009,\"username\":\"user-9\",\"sex\":\"女\",\"city\":\"城市-9\",\"sign\":\"签名-9\",\"experience\":484,\"logins\":25,\"wealth\":86801934,\"classify\":\"词人\",\"score\":75},{\"id\":10010,\"username\":\"user-10\",\"sex\":\"女\",\"city\":\"城市-10\",\"sign\":\"签名-10\",\"experience\":1016,\"logins\":182,\"wealth\":71294671,\"classify\":\"诗人\",\"score\":34},{\"id\":10011,\"username\":\"user-11\",\"sex\":\"女\",\"city\":\"城市-11\",\"sign\":\"签名-11\",\"experience\":492,\"logins\":107,\"wealth\":8062783,\"classify\":\"诗人\",\"score\":6},{\"id\":10012,\"username\":\"user-12\",\"sex\":\"女\",\"city\":\"城市-12\",\"sign\":\"签名-12\",\"experience\":106,\"logins\":176,\"wealth\":42622704,\"classify\":\"词人\",\"score\":54},{\"id\":10013,\"username\":\"user-13\",\"sex\":\"男\",\"city\":\"城市-13\",\"sign\":\"签名-13\",\"experience\":1047,\"logins\":94,\"wealth\":59508583,\"classify\":\"诗人\",\"score\":63},{\"id\":10014,\"username\":\"user-14\",\"sex\":\"男\",\"city\":\"城市-14\",\"sign\":\"签名-14\",\"experience\":873,\"logins\":116,\"wealth\":72549912,\"classify\":\"词人\",\"score\":8},{\"id\":10015,\"username\":\"user-15\",\"sex\":\"女\",\"city\":\"城市-15\",\"sign\":\"签名-15\",\"experience\":1068,\"logins\":27,\"wealth\":52737025,\"classify\":\"作家\",\"score\":28},{\"id\":10016,\"username\":\"user-16\",\"sex\":\"女\",\"city\":\"城市-16\",\"sign\":\"签名-16\",\"experience\":862,\"logins\":168,\"wealth\":37069775,\"classify\":\"酱油\",\"score\":86},{\"id\":10017,\"username\":\"user-17\",\"sex\":\"女\",\"city\":\"城市-17\",\"sign\":\"签名-17\",\"experience\":1060,\"logins\":187,\"wealth\":66099525,\"classify\":\"作家\",\"score\":69},{\"id\":10018,\"username\":\"user-18\",\"sex\":\"女\",\"city\":\"城市-18\",\"sign\":\"签名-18\",\"experience\":866,\"logins\":88,\"wealth\":81722326,\"classify\":\"词人\",\"score\":74},{\"id\":10019,\"username\":\"user-19\",\"sex\":\"女\",\"city\":\"城市-19\",\"sign\":\"签名-19\",\"experience\":682,\"logins\":106,\"wealth\":68647362,\"classify\":\"词人\",\"score\":51},{\"id\":10020,\"username\":\"user-20\",\"sex\":\"男\",\"city\":\"城市-20\",\"sign\":\"签名-20\",\"experience\":770,\"logins\":24,\"wealth\":92420248,\"classify\":\"诗人\",\"score\":87},{\"id\":10021,\"username\":\"user-21\",\"sex\":\"男\",\"city\":\"城市-21\",\"sign\":\"签名-21\",\"experience\":184,\"logins\":131,\"wealth\":71566045,\"classify\":\"词人\",\"score\":99},{\"id\":10022,\"username\":\"user-22\",\"sex\":\"男\",\"city\":\"城市-22\",\"sign\":\"签名-22\",\"experience\":739,\"logins\":152,\"wealth\":60907929,\"classify\":\"作家\",\"score\":18},{\"id\":10023,\"username\":\"user-23\",\"sex\":\"女\",\"city\":\"城市-23\",\"sign\":\"签名-23\",\"experience\":127,\"logins\":82,\"wealth\":14765943,\"classify\":\"作家\",\"score\":30},{\"id\":10024,\"username\":\"user-24\",\"sex\":\"女\",\"city\":\"城市-24\",\"sign\":\"签名-24\",\"experience\":212,\"logins\":133,\"wealth\":59011052,\"classify\":\"词人\",\"score\":76},{\"id\":10025,\"username\":\"user-25\",\"sex\":\"女\",\"city\":\"城市-25\",\"sign\":\"签名-25\",\"experience\":938,\"logins\":182,\"wealth\":91183097,\"classify\":\"作家\",\"score\":69},{\"id\":10026,\"username\":\"user-26\",\"sex\":\"男\",\"city\":\"城市-26\",\"sign\":\"签名-26\",\"experience\":978,\"logins\":7,\"wealth\":48008413,\"classify\":\"作家\",\"score\":65},{\"id\":10027,\"username\":\"user-27\",\"sex\":\"女\",\"city\":\"城市-27\",\"sign\":\"签名-27\",\"experience\":371,\"logins\":44,\"wealth\":64419691,\"classify\":\"诗人\",\"score\":60},{\"id\":10028,\"username\":\"user-28\",\"sex\":\"女\",\"city\":\"城市-28\",\"sign\":\"签名-28\",\"experience\":977,\"logins\":21,\"wealth\":75935022,\"classify\":\"作家\",\"score\":37},{\"id\":10029,\"username\":\"user-29\",\"sex\":\"男\",\"city\":\"城市-29\",\"sign\":\"签名-29\",\"experience\":647,\"logins\":107,\"wealth\":97450636,\"classify\":\"酱油\",\"score\":27}]}";
        return json;


    }


    public static void main(String[] args) throws Exception {
        ArrayList laagents = new ArrayList<>();
        Laagent laagent = new Laagent();
        laagent.setAgentcode("000001");
        laagents.add(laagent);

        Object object = laagents.get(0);
        //获取反射对象   通过传递的属性获取属性值
        Class clazz = object.getClass();
        //将传递的属性首字母大写
        Method method = clazz.getMethod("getAgentcode");
        Object attribute = method.invoke(object);
        System.out.println(attribute);
    }


}
