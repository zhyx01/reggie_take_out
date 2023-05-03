package com.ax.reggie.controller;

import com.ax.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * className: CommonController
 * description: 文件上传下载
 *
 * @author: axiang
 * date: 2023/5/1 0001 20:54
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;

    /**
     * description: 上传文件
     * @param file: 文件
     * @return: R<String> <br>
     * date: 2023/5/1 0001 <br>
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        // file是一个临时文件, 需要转存到指定位置, 否则本次请求完成后临时文件会被删除

        // 获取原始文件名
        String originalFilename = file.getOriginalFilename();
        // 获取文件名后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        // 使用UUID重新生成文件名, 防止文件名称重复造成文件覆盖
        String fileName = UUID.randomUUID().toString() + suffix;

        // 创建一个目录对象, 如果文件保存目录不存在, 先创建目录
        File dir = new File(basePath);
        // 判断是否存在
        if (!dir.exists()) {
            // 目录不存在, 需要创建
            dir.mkdirs();
        }

        // 经临时文件转存到指定位置
        try {
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return R.success(fileName);
    }

    /**
     * description: 文件下载
     * @param name: 文件名
     * @param response:
     * @return: void <br>
     * date: 2023/5/1 0001 <br>
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {

        try {
            // 输入流, 通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));

            // 输出流, 通过输出流将文件写回浏览器, 在浏览器展示图片
            ServletOutputStream outputStream = response.getOutputStream();

            // 设置响应请求头
            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];

            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }

            // 关闭资源
            outputStream.close();
            fileInputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
