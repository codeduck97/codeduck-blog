package com.duck.code.commons.utils;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

/**
 * @program: codeduck-blog-serve
 * @description: 图片处理工具类
 * @author: Code Duck
 * @create: 2020-11-04 09:46
 */
@Slf4j
public class PictureUtil {

    public static final String[] IMG_FILE = {"bmp", "jpg", "png", "tif", "gif", "jpeg", "webp"};

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

    /**
     * desc: 判断图片文件后缀名是否合法
     * <p>
     *
     * @param suffix
     * @return
     */
    public static boolean isPic(String suffix) {
        for (int i = 0; i < IMG_FILE.length; i++) {
            if (IMG_FILE[i].equals(suffix)) {
                return true;
            }
        }
        return false;
    }

    // 存储文件：目录+生成的文件名称
    // nigx访问地址 = "http://localhost:8600/" + 新的文件名
    // 生成markdown地址：![2009005](http://192.168.1.105:8090/upload/2020/11/2009005-2072765a210a4850aecc89508ce9a549.jpg)

    /**
     * desc: 生成markdown图片地址
     * <p>
     *
     * @param path 文件上传路径名
     * @return
     */
    public static String generateMdUrl(String path,String name){
        LocalDateTime now = LocalDateTime.now();
        Integer Y = now.getYear();
        Integer M = now.getMonth().getValue();
        return "![" + Y.toString() +M.toString() +"](" +generatePicUrl(path,name) +")";

    }

    /**
     * desc: 生成markdown图片地址
     * <p>
     *
     * @param url 将网络图片地址转为markdown图片地址
     * @return
     */
    public static String generateMdUrl(String url){
        LocalDateTime now = LocalDateTime.now();
        Integer Y = now.getYear();
        Integer M = now.getMonth().getValue();
        return "![" + Y.toString() +M.toString() +"](" + url +")";

    }


    /**
     * desc: 生成服务器文件访问地址
     * http://192.168.1.105:8600/upload/2020/11/2009005-2072765a210a4850aecc89508ce9a549.jpg
     * <p>
     *
     * @param path 文件上传路径
     * @param path 文件名
     * @return
     */
    public static String generatePicUrl(String path, String name){
        String url = null;
        try {
            String address = InetAddress.getLocalHost().getHostAddress();
            String localhost = "localhost";
//            url = "http://" + address + ":8600/" + path + "/" + name;
            url = "http://" + localhost + ":8600/" + path + "/" + name;

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return url;
    }
}
