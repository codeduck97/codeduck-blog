package com.duck.code.file.utils;

import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

/**
 * @program: elasticsearch-serve
 * @description: 文件处理工具
 * @author: Code Duck
 * @create: 2020-09-17 09:29
 **/
public class FileUtil {

    /**
     * 获取文件后缀名
     */
    public static String getSuffix(MultipartFile file){
        if (file.isEmpty()) return "";
        String filename = file.getOriginalFilename();
        if (filename.lastIndexOf(".") == -1) return "";
        return filename.substring(filename.lastIndexOf("."));
    }

    /**
     * desc: 判断后缀名是否合法
     * <p>
     * @param suffix
     * @return
     */
    public static boolean isSuffix(String suffix){
        HashSet<String> set = new HashSet<>();
        set.add(".doc");
        set.add(".docx");
        return set.contains(suffix);
    }

    /**
     * 获取.doc文件内容
     */
    public static String readDoc(MultipartFile file){

        if (file.isEmpty()){
            return "";
        }

        WordExtractor wordExtractor = null;
        try {
            InputStream inputStream = file.getInputStream();
            wordExtractor = new WordExtractor(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordExtractor.getText();
    }

    /**
     * desc: 读取.docx文件的内容
     * <p>
     * @param file
     * @return
     */
    public static String readDocx(MultipartFile file){
        if (file.isEmpty())  return "";

        String text = null;
        try {
            InputStream inputStream = file.getInputStream();
            XWPFDocument xwpfDocument = new XWPFDocument(inputStream);
            XWPFWordExtractor xwpfWordExtractor = new XWPFWordExtractor(xwpfDocument);
            text = xwpfWordExtractor.getText();
            System.out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
