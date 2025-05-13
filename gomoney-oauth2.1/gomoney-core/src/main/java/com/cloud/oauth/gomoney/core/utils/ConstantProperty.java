package com.cloud.oauth.gomoney.core.utils;

public class ConstantProperty {
    public static final String CATEGORY_TEST = "pics";
    /**
     * 数据请求返回码
     */
    public static final int RESCODE_SUCCESS = 1000;				//成功
    public static final int RESCODE_SUCCESS_MSG = 1001;			//成功(有返回信息)
    public static final int RESCODE_EXCEPTION = 1002;			//请求抛出异常
    public static final int RESCODE_NOLOGIN = 1003;				//未登陆状态
    public static final int RESCODE_NOEXIST = 1004;				//查询结果为空
    public static final int RESCODE_NOAUTH = 1005;				//无操作权限

    public static final String BASE64KEY="MD5";//密码密匙

    /**
     * jwt
     */
    public static final String JWT_ID = "jwt";
    public static final String JWR_TOKEN_TYPE="Bearer";
    public static final String JWR_TOKEN_BLANK="Bearer ";
    public static final String JWT_SECRET = "7786df7fc3a34e26a61c034d5ec8245d";
    public static final int JWT_TTL = 20*1000; // 60*60*1000;  //millisecond
    public static final int JWT_REFRESH_INTERVAL = 18*1000; //55*60*1000;  //millisecond
    public static final int JWT_REFRESH_TTL = 60*1000; // 12*60*60*1000;  //millisecond

    /** 超级管理员ID */
    public static final int SUPER_ADMIN = 1;

    // public static final String BASE64KEY="";
    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     *  升序
     */
    public static final String ASC = "asc";
    /**
     * 菜单类型
     *
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年11月15日 下午1:24:29
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 定时任务状态
     *
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年12月3日 上午12:07:22
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}