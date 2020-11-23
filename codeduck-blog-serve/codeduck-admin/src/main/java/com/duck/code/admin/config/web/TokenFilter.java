package com.duck.code.admin.config.web;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import java.io.IOException;

/**
 * @program: codeduck-blog-serve
 * @description: token过滤器
 * @author: Code Duck
 * @create: 2020-11-09 19:28
 */
@Configuration
public class TokenFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
