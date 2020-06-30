package com.example.demo.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @auther chenyong
 * @date 2020/6/29 14:41
 */
@Controller
@Slf4j
@RequestMapping(value="/imageDownload/")
public class ImageDownloadController {

    @RequestMapping(value = "zipImages",method = RequestMethod.GET)
    public void downloadZipImages(HttpServletResponse response){
        String[] imagesUrlArr={
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202002/1/7-436da7ffac8b4f079cff2b67872e1de0-g-o-0.jpg",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202002/1/7-d1278f5970534c2db72ca80f0a591784-g-o-0.png",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202003/302/4-f60e085ccfbc4cf9b83fb64ad1b53c98-g-o-0.jpg"
        };
        try {
            String downloadFilename = "XXX.zip";// 文件的名称
            downloadFilename = URLEncoder.encode(downloadFilename, "UTF-8");// 转换中文否则可能会产生乱码
            response.setContentType("application/octet-stream");// 指明response的返回对象是文件流
            response.setHeader("Content-Disposition", "attachment;filename=" + downloadFilename);// 设置在下载框默认显示的文件名
            ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
            for (int i = 0; i < imagesUrlArr.length; i++) {
                URL url = new URL(imagesUrlArr[i]);
                zos.putNextEntry(new ZipEntry(i + ".jpg"));//在压缩文件中建立名字为XXX的文件
                BufferedImage buffImg = ImageIO.read(url);//读取图片
                BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_BGR);
                Graphics graphics = image.createGraphics();
                graphics.drawImage(buffImg, 0, 0, 200, 200, null);//绘制缩小后的图
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", os);
                InputStream fis = new ByteArrayInputStream(os.toByteArray());
                byte[] buffer = new byte[1024];
                int r = 0;
                while ((r = fis.read(buffer)) != -1) {
                    zos.write(buffer, 0, r);
                }
                fis.close();
            }
            zos.flush();
            zos.close();
        } catch (Exception e) {
            log.error("下载XXX错误", e);
        }
    }
}
