package com.duck.code.commons.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

/**
 * @program: codeduck-blog-serve
 * @description: 文件处理工具
 * @author: Code Duck
 * @create: 2020-11-04 13:47
 */
@Slf4j
public class FileUtil {

    /**
     * desc: 生成文件存储目录 /upload/year/month
     * <p>
     *
     * @param
     * @return
     */
    public static String createDir(){
        LocalDateTime now = LocalDateTime.now();
        Integer Y = now.getYear();
        Integer M = now.getMonth().getValue();
        return "upload/images/" +Y.toString() + "/" + M;
    }
    /**
     * desc: 生成的文件名为年月-UUID
     * <p>
     *
     * @param suffix 后缀名
     * @return
     */
    public static String createFileName(String suffix){
        LocalDateTime now = LocalDateTime.now();
        Integer Y = now.getYear();
        Integer M = now.getMonth().getValue();
        Integer D = now.getDayOfMonth();
        UUID uid = UUID.randomUUID();
        return Y.toString() + M.toString() + D.toString() + "-" + uid.toString() +"." + suffix;
    }

    /**
     * desc: 根据当前时间生成一个数字名称
     * <p>
     *
     * @param
     * @return
     */
    public static String generateName() {
        LocalDateTime now = LocalDateTime.now();
        Integer Y = now.getYear();
        Integer M = now.getMonth().getValue();
        Integer D = now.getDayOfMonth();
        Random random = new Random();
        Integer i = random.nextInt(99);
        return Y.toString() + M.toString() + D.toString() +i.toString();
    }


    /**
     * 将 File 转换成 MultipartFile
     *
     * @param file
     * @return
     */
    public static MultipartFile fileToMultipartFile(File file) {
        MockMultipartFile mockMultipartFile = null;
        try {
            FileInputStream i = new FileInputStream(file);
            mockMultipartFile = new MockMultipartFile(file.getName(), i);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mockMultipartFile;
    }

    /**
     * desc: multipartFile
     * <p>
     *
     * @param file
     * @return
     */
    public static File multipartFileToFile(MultipartFile file) {
        File f = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            try {
                ins = file.getInputStream();
                f = new File(file.getOriginalFilename());
                inputStreamToFile(ins, f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }

    public static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * desc: 获取文件后缀名
     * <p>
     *
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName) {
        return fileName.substring(fileName.indexOf('.') + 1);
    }


}
