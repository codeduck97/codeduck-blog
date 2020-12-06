package com.duck.code.file.config.swagger;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: codeduck-auth
 * @description: Swagger配置类
 * @author: Code Duck
 * @create: 2020-09-27 15:40
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * desc: 全局参数配置：配置token请求令牌
     * <p>
     *
     * @param
     * @return
     */
    private List<Parameter> parameter() {
        List<Parameter> params = new ArrayList<>();
        params.add(
                new ParameterBuilder().name("Authorization")
                        .description("请求令牌")
                        .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .required(false).build()
        );
        return params;
    }

    /**
     * desc:全局参数配置：响应信息配置
     * <p>
     *
     * @param
     * @return
     */
    private List<ResponseMessage> responseMessageList() {

        List<ResponseMessage> responseMessageList = new ArrayList<>();
        responseMessageList.add(new ResponseMessageBuilder().code(1000).message("操作成功").responseModel(new ModelRef("R")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(1001).message("操作失败").responseModel(new ModelRef("R")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(1002).message("拒绝执行本次请求").responseModel(new ModelRef("R")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(5000).message("token异常").responseModel(new ModelRef("R")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(5001).message("token失效").responseModel(new ModelRef("R")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(6000).message("服务器异常").responseModel(new ModelRef("R")).build());
        return responseMessageList;
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                // 添加全局响应状态信息
                .globalResponseMessage(RequestMethod.GET, responseMessageList())
                .globalResponseMessage(RequestMethod.POST, responseMessageList())
                .globalResponseMessage(RequestMethod.PUT, responseMessageList())
                .globalResponseMessage(RequestMethod.DELETE, responseMessageList())
                .select()
                // 显示所有加了注解的class
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(parameter());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Code Duck Blog 文件服务器API文档")
                .description("本Api文档在逐步完善……")
                .termsOfServiceUrl("http://127.0.0.1:8203")
                .contact(new Contact("Code Duck", "", "codeduck@163.com"))
                .version("1.0")
                .build();
    }
}
