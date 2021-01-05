package com.duck.code.commons.feign;

import com.baomidou.mybatisplus.extension.api.R;
import com.duck.code.commons.config.FeignConfig;
import com.duck.code.commons.entity.file.Contract;
import com.duck.code.commons.entity.picture.Picture;
import feign.Headers;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

/**
 * @program: codeduck-blog-serve
 * @description:
 * @author: Code Duck
 * @create: 2020-12-05 19:26
 **/
@FeignClient(name = "codeduck-file")
public interface PictureFeignClient {

    @PostMapping("/api/picture/list")
    Map<String, Object> getPictureList(@RequestParam(value = "pageNum") long pageNum, @RequestParam(value = "pageSize") long pageSize);

    /**
     * 使用feign进行文件传输时，需要设置文件文件传输类型为：multipart/form-data
     * 个人理解：MultipartFile是Spring对传输文件的封装格式，而feign使用的是http原生的数据传输格式进行文件传输
     */
    @PostMapping(value = "/api/picture/upload/serve", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Picture uploadPicsToServe(@RequestPart("file") MultipartFile picture);

    @PostMapping(value = "/api/picture/upload/qiniu", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Picture uploadPicsToQinNiu(@RequestPart("file") MultipartFile picture);

    @PostMapping("/api/picture/delete")
    boolean deletePicture(@RequestParam("pictureIds") String[] pictureIds);

    @PostMapping("/api/contract/upload")
    boolean uploadContract(@RequestBody Contract contract);

}
