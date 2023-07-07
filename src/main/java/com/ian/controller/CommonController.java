package com.ian.controller;

import com.ian.entity.Result;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * 文件上傳及下載
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${project.path}")
    private String path;

    /**
     * 文件上傳
     * @param file
     * @return
     */

    @RequestMapping("/upload")
    public Result<String> upload(MultipartFile file){
        //此文件近來為一臨時文件，需要轉存到指定位子
        log.info(file.toString());

        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        //使用uuid防止文件名稱重複

        File dir = new File(path);
        //判斷當前目錄是否存在
        if (!dir.exists()){
            //目錄不存在需要創目錄
            dir.mkdirs();
        }

        String newFilename = UUID.randomUUID().toString()+suffix;
        try {
            //將臨時文件轉存到以下路徑
            file.transferTo(new File(path+newFilename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Result.success(newFilename);

    }

    /**
     * 文件下載
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        //輸入流，通過輸入流讀取文件內容
        FileInputStream fileInputStream = null;
        ServletOutputStream outputStream = null;
        try {
            fileInputStream= new FileInputStream(new File(path+name));
            //輸出流，通過輸出流將文件寫回瀏覽器，在瀏覽器展示圖片
            outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            int len=0;
            byte[] bytes = new byte[1024];

            while ((len = fileInputStream.read(bytes))!= -1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                fileInputStream.close();
                outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //輸出流，通過輸出流將文件寫回瀏覽器，在瀏覽器展示圖片

    }
}
