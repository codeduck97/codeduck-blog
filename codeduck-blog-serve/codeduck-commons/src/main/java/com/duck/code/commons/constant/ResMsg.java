package com.duck.code.commons.constant;

/**
 * @program: codeduck-blog-serve
 * @description: 响应信息
 * @author: Code Duck
 * @create: 2020-10-24 13:54
 */
public class ResMsg {

    public static final String OPERATION_SUCCESS = "操作成功";
    public static final String OPERATION_FAIL = "操作失败";
    public static final String INSERT_SUCCESS = "插入成功";
    public static final String INSERT_FAIL = "插入失败";
    public static final String UPDATE_SUCCESS = "更新成功";
    public static final String UPDATE_FAIL = "更新失败";
    public static final String DELETE_SUCCESS = "删除成功";
    public static final String DELETE_FAIL = "删除失败";

    public static final String ENTITY_EXIST = "该实体已存在";
    public static final String ENTITY_NOT_EXIST = "该实体不存在";
    public static final String SYSTEM_CONFIG_NOT_EXIST = "系统配置有误";
    public static final String PLEASE_SET_QI_NIU = "请先配置七牛云";
    public static final String PLEASE_SET_LOCAL = "请先配置本地图片域名";


    public static final String KEYWORD_IS_NOT_EMPTY = "关键字不能为空";

    public static final String COMMENT_IS_NOT_EXIST = "该评论不存在";

    public static final String NO_COMMENTS_OPEN = "网站未开启评论功能";

    public static final String BLOG_NO_OPEN_COMMENTS = "本博客未开启评论功能";

    public static final String LOGIN_DISABLE = "该账号已被封禁";
    public static final String LOGIN_ERROR = "用户名或密码错误，错误%d次后，账户将被锁定30分钟";
    public static final String NO_ROLE = "没有分配角色权限";
    public static final String INTERFACE_FREQUENTLY = "接口过于频繁调用";
    public static final String PARAM_INCORRECT = "传入参数有误！";
    public static final String BLOG_UNDER_THIS_SORT = "该分类下还有博客！";
    public static final String RESOURCE_UNDER_THIS_SORT = "该分类下还有资源！";
    public static final String SUBJECT_UNDER_THIS_SORT = "该专题下还有内容！";
    public static final String PICTURE_UNDER_THIS_SORT = "该分类下还有图片！";
    public static final String DICT_DATA_UNDER_THIS_SORT = "该分类下还有字典数据！";
    public static final String BLOG_UNDER_THIS_TAG = "该标签下还有博客！";
    public static final String ADMIN_UNDER_THIS_ROLE = "该角色下还有管理员！";
    public static final String DELETE_ADMIN_ACCOUNT_ERROR = "无法删除Admin账号！";
    public static final String CHILDREN_MENU_UNDER_THIS_MENU = "该菜单下还有子菜单！";
    public static final String THIS_SORT_IS_TOP = "该分类已经在顶端！";
    public static final String THIS_TAG_IS_TOP = "该标签已经在顶端！";
    public final static String INVALID_TOKEN = "token令牌未被识别";
    public final static String SERVER_ERROR = "服务器目前繁忙，请稍等片刻后重试";
    public final static String ERROR_PASSWORD = "密码错误";
    public final static String YOU_HAVE_BEEN_PRISE = "您已经点赞过了!";
    public final static String PLEASE_LOGIN_TO_PRISE = "请先登录后才能点赞!";
    public final static String The_PICTURE_SORT_DOES_NOT_EXIST = "该图片分类不存在";
    public final static String The_PICTURE_DOES_NOT_EXIST = "该图片不存在";

    public final static String PICTURE_MUST_BE_SELECT_AREA = "图片必须选择上传到一个区域";
    public final static String MUST_BE_OPEN_LOCAL_UPLOAD = "图片显示优先级为本地优先，必须开启图片上传本地";
    public final static String MUST_BE_OPEN_QI_NIU_UPLOAD = "图片显示优先级为七牛云优先，必须开启图片上传七牛云";

    public final static String MUST_BE_SET_EMAIL = "开启邮件通知，必须设置邮箱地址";

    public static final String DATA_NO_PRIVILEGE = "该数据无权限访问";

    public static final String CANNOT_CHANGE_THE_PASSWORD_BY_USER = "第三方登录的用户无法修改密码";

    public static final String RESTAPI_NO_PRIVILEGE = "您无权进行该操作";

    public static final String ACCESS_NO_PRIVILEGE = "该资源无权限访问";

    public static final String LOGIN_TIMEOUT = "您已退出，请重新登录";

    public static final String PLEASE_CONFIGURE_A_PASSWORD = "请先到参数配置，配置默认密码";
    public static final String ACCOUNT_DISABLE = "该账号不可用";
}
