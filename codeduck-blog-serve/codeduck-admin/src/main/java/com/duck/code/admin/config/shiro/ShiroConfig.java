package com.duck.code.admin.config.shiro;

import com.duck.code.admin.filter.JwtFilter;
import com.duck.code.admin.filter.PermissionFilter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @program: codeduck-auth
 * @description: Shiro配置类
 * @author: Code Duck
 * @create: 2020-09-27 16:17
 */
@Configuration
public class ShiroConfig {

    // 1、创建 realm 对象
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

    // 2、DefaultWebSecurityManager
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 关联UserRealm
        securityManager.setRealm(userRealm());

        // 关闭Shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }

    // 3、ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {

        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();

        // 添加自定义过滤器取名为jwtFilter
        Map<String, Filter> filtersMap = new HashMap<String, Filter>();
        filtersMap.put("jwtFilter", jwtFilter());
        filterFactoryBean.setFilters(filtersMap);
        filterFactoryBean.setSecurityManager(securityManager);

        //访问权限判断过滤器
        // filtersMap.put("permit", permissionFilter());

        // 定义拦截器.
        Map<String, String> filterRuleMap = new LinkedHashMap<String, String>();
        // 配置不会被拦截的链接 顺序判断，，必须配置到每个静态目录

        filterRuleMap.put("/static/css/**", "anon");
        filterRuleMap.put("/static/fonts/**", "anon");
        filterRuleMap.put("/favicon.ico", "anon");
        filterRuleMap.put("/static/img/**", "anon");
        filterRuleMap.put("/static/js/**", "anon");

        filterRuleMap.put("/login", "anon");

        // swagger2免登录设置
        filterRuleMap.put("/webjars/**", "anon");
        filterRuleMap.put("/swagger/**", "anon");
        filterRuleMap.put("/v2/api-docs", "anon");
        filterRuleMap.put("/swagger-ui.html", "anon");
        filterRuleMap.put("/swagger-resources/**", "anon");
        filterRuleMap.put("/doc.html", "anon");


        /**
         * 加入自定义过滤器
         * 1.由于我们要做前后端分离，所以这里我们不需要shiro默认的过滤器authc,因为该过滤器在用户没有登陆的时候
         *   会跳转到登陆页，不符合我们的逻辑处理
         * 2.这里我们通过自定义一个登陆判断的过滤器jwt.这样的话如果用户没有登陆我们直接返回json数据
         * */
        filterRuleMap.put("/api/**", "jwtFilter");

        filterFactoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return filterFactoryBean;
    }

    /**
     * <pre>
     * 注入bean，此处应注意：
     *
     * (1)代码顺序，应放置于shiroFilter后面，否则报错：
     * 	No SecurityManager accessible to the calling code, either bound to the org.apache.shiro.util.
     * 	ThreadContext or as a vm static singleton. This is an invalid application configuration.
     *
     * (2)如不在此注册，在filter中将无法正常注入bean
     * </pre>
     */
    @Bean("jwtFilter")
    public JwtFilter jwtFilter() {

        return new JwtFilter();
    }

    public PermissionFilter permissionFilter() {

        return new PermissionFilter();
    }

}
