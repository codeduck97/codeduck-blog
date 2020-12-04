## 项目介绍

CodeDuck Blog 是一个前后分离的博客项目。

后端技术栈为：

- `Springboot` + `SpringCloud Alibaba` +`Mybatis-Plus` 进行开发。`Nacos`作为服务注册中心。

- 登录认证使用`JWT` + `Shiro` + `Redis`，前端根据后端返回的角色权限信息进行动态渲染。
- 使用`Swagger2`作为开发文档API记录工具。
- 使用`Elasticsearch`框架作为检索引擎（马上就做……）
- 支持图片上传至本地或者[七牛云存储](https://www.qiniu.com/)

前端主要使用 [VUE](https://cn.vuejs.org/) + [Element UI](https://element.eleme.cn/#/zh-CN) + [vue-element-admin](https://github.com/PanJiaChen/vue-element-admin/)来做博客门户和后台管理系统（前端比较业余，边学边做，慢慢完善……）

## 已完成的功能

### 后台管理系统

| ![image-20201204180620231](https://jason-01.oss-cn-hangzhou.aliyuncs.com/public/image/markdown/image-20201204180620231.png) | ![image-20201126170109996](https://jason-01.oss-cn-hangzhou.aliyuncs.com/public/image/markdown/image-20201126170109996.png) |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| ![image-20201204180814232](https://jason-01.oss-cn-hangzhou.aliyuncs.com/public/image/markdown/image-20201204180814232.png) | ![image-20201204180840128](https://jason-01.oss-cn-hangzhou.aliyuncs.com/public/image/markdown/image-20201204180840128.png) |
| ![image-20201204181101055](https://jason-01.oss-cn-hangzhou.aliyuncs.com/public/image/markdown/image-20201204181101055.png) | ![image-20201204181301479](https://jason-01.oss-cn-hangzhou.aliyuncs.com/public/image/markdown/image-20201204181301479.png) |
| ![image-20201126170122122](https://jason-01.oss-cn-hangzhou.aliyuncs.com/public/image/markdown/image-20201126170122122.png) | ![image-20201126170239369](https://jason-01.oss-cn-hangzhou.aliyuncs.com/public/image/markdown/image-20201126170239369.png) |
| ![image-20201126170132479](https://jason-01.oss-cn-hangzhou.aliyuncs.com/public/image/markdown/image-20201126170132479.png) | ![image-20201126170310605](https://jason-01.oss-cn-hangzhou.aliyuncs.com/public/image/markdown/image-20201126170310605.png) |
| ![image-20201126170330081](https://jason-01.oss-cn-hangzhou.aliyuncs.com/public/image/markdown/image-20201126170330081.png) | ![image-20201126170401522](https://jason-01.oss-cn-hangzhou.aliyuncs.com/public/image/markdown/image-20201126170401522.png) |
| ![image-20201126170432770](https://jason-01.oss-cn-hangzhou.aliyuncs.com/public/image/markdown/image-20201126170432770.png) | ![image-20201126170447051](https://jason-01.oss-cn-hangzhou.aliyuncs.com/public/image/markdown/image-20201126170447051.png) |



### 前端门户

| ![image-20201126165658455](https://jason-01.oss-cn-hangzhou.aliyuncs.com/public/image/markdown/image-20201126165658455.png) | ![image-20201126165731377](https://jason-01.oss-cn-hangzhou.aliyuncs.com/public/image/markdown/image-20201126165731377.png) |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| ![image-20201126165804886](https://jason-01.oss-cn-hangzhou.aliyuncs.com/public/image/markdown/image-20201126165804886.png) | ![image-20201126165818103](https://jason-01.oss-cn-hangzhou.aliyuncs.com/public/image/markdown/image-20201126165818103.png) |
| ![image-20201126165835815](https://jason-01.oss-cn-hangzhou.aliyuncs.com/public/image/markdown/image-20201126165835815.png) | 未完待续……                                                   |

### Swagger API文档

| ![image-20201126181146056](https://jason-01.oss-cn-hangzhou.aliyuncs.com/public/image/markdown/image-20201126181146056.png) | ![image-20201126181251317](https://jason-01.oss-cn-hangzhou.aliyuncs.com/public/image/markdown/image-20201126181251317.png) |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| 待续……                                                       |                                                              |



## 致谢

**感谢蘑菇博客：** https://gitee.com/moxi159753/mogu_blog_v2 

**感谢Vue后台管理模板：**https://github.com/PanJiaChen/vue-element-admin

**感谢FEBS权限框架：**https://github.com/wx7614140/FEBS-Vue-Fix