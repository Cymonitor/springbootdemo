package com.example.demo.common.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @auther chenyong
 * @date 2020/6/28 14:13
 */
@Controller
@RequestMapping(value="/picDownload/")
public class PicDownloadController {


    /**
     * 根据图片地址下载图片并打包成zip
     * @param request
     * @param response
     */
    @RequestMapping(value = "zipPics",method = RequestMethod.GET)
    public void downloadZipPics(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String[] picUrlsArr={
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202002/1/7-436da7ffac8b4f079cff2b67872e1de0-g-o-0.jpg",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202002/1/7-d1278f5970534c2db72ca80f0a591784-g-o-0.png",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202003/302/4-f60e085ccfbc4cf9b83fb64ad1b53c98-g-o-0.jpg"
        };
        String zipName="CY_zip";
        byte[] data=getPicsBytes(picUrlsArr);
        //清楚首部空白行
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + zipName + ".zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        //输出解压包
        IOUtils.write(data, response.getOutputStream());
        //关闭流而不用抛异常
        IOUtils.closeQuietly(response.getOutputStream());
    }

    private byte[] getPicsBytes(String[] picUrls) throws Exception{
        ByteArrayOutputStream arrayOutputStream=new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream=new ZipOutputStream(arrayOutputStream);
        for(String picUrl:picUrls){
            String picName=picUrl.substring(picUrl.lastIndexOf("/")+1);
            downloadPics(picUrl,picName,zipOutputStream);
        }
        IOUtils.closeQuietly(zipOutputStream);
        return arrayOutputStream.toByteArray();
    }

    private void downloadPics(String picUrl,String picName,ZipOutputStream zipOutputStream) throws Exception{
        String[] picsSuffixArr={"jpg","png","gif","jpeg","bmp"};
        List<String> picsSuffixList= Arrays.asList(picsSuffixArr);
        String picSuffix=picUrl.substring(picUrl.lastIndexOf(".")+1);
        if(!StringUtils.isEmpty(picSuffix)){
            BufferedInputStream in=null;
            if(!picsSuffixList.contains(picSuffix)){
               throw new Exception("图片格式有误，无法下载");
            }
            try{
                URL url = new URL(picUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5 * 1000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty(
                        "Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, "
                                + "application/x-shockwave-flash, application/xaml+xml, "
                                + "application/vnd.ms-xpsdocument, application/x-ms-xbap, "
                                + "application/x-ms-application, application/vnd.ms-excel, "
                                + "application/vnd.ms-powerpoint, application/msword, */*");
                conn.setRequestProperty("Accept-Language", "zh-CN");
                conn.setRequestProperty("Charset", "UTF-8");

                InputStream inStream = conn.getInputStream();
                if(inStream == null) {
                    throw new Exception("获取压缩的数据项失败! 图片文件为：" + picName);
                }else {
                    in = new BufferedInputStream(inStream);
                }

                // 压缩条目不是具体独立的文件，而是压缩包文件列表中的列表项，称为条目，就像索引一样
                //ZipEntry zipEntry = new ZipEntry("图片/" + imageName);

                ZipEntry zipEntry = new ZipEntry("CK/"+picName);
                // 定位到该压缩条目位置，开始写入文件到压缩包中
                zipOutputStream.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024 * 5]; // 读写缓冲区(每张图片限定5M以内)
                int read = 0;
                while ((read = in.read(bytes)) != -1) {
                    zipOutputStream.write(bytes, 0, read);
                }

                IOUtils.closeQuietly(inStream); // 关掉输入流
                IOUtils.closeQuietly(in); // 关掉缓冲输入流
                zipOutputStream.closeEntry();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
