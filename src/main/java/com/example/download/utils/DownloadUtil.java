package com.example.download.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @Project jspdemo
 * @ClassName DownloadUtil
 * @Author MPF
 * @Date 2020/5/24 0024 23:55
 * @Version 1.0
 * @Description TODO
 **/
public class DownloadUtil {

    /**
     * 文件下载工具类
     *
     * @param response   响应
     * @param attachName 附件名称
     * @param path       附件路径
     */
    public static void download(HttpServletResponse response, String attachName, String path) throws Exception {
        //读取指定路径下面的文件
        File file = new File(path);
        //自行关闭的流
        try (InputStream in = new FileInputStream(file); OutputStream outputStream = new BufferedOutputStream(response.getOutputStream())) {
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(attachName, "UTF-8"));

            //创建存放文件内容的数组
            byte[] buff = new byte[1024];
            //所读取的内容使用n来接收
            int n;
            //当没有读取完时,继续读取,循环
            while ((n = in.read(buff)) != -1) {
                //将字节数组的数据全部写入到输出流中
                outputStream.write(buff, 0, n);
            }
            //强制将缓存区的数据进行输出
            outputStream.flush();
            //关流
            outputStream.close();
            in.close();
        }
    }
}
