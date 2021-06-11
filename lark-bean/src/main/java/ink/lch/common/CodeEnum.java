package ink.lch.common;

public enum CodeEnum implements BaseErrorInfoInterface {
    // 通用处理值
    SUCCESS("系统正常", "0"),
    SERVER_ERROR("系统繁忙", "503"),
    INTERNAL_SERVER_ERROR("服务器内部错误!", "500"),
    NOT_FOUND("未找到该资源!", "404"),
    NOT_EMPTY("数据不能为空", "201"),

    // 邮箱返回值
    EMAIL_USED("邮箱已使用", "-100"),
    EMAIL_UNUSED("邮箱未使用", "100"),
    LOGIN_ERROR("账号或密码错误", "-102"),
    EMAIL_ACTIVE_ERROR("激活邮件发送失败", "-103"),
    CODE_ERROR("验证码错误", "-104"),

    SQL_ERROR("数据库异常", "-201"),

    LINK_USED("链接已存在", "-301"),

    DOMAIN_USED("用户名已使用", "-400"),
    DOMAIN_UNUSED("用户名未使用", "400"),

    CATEGORY_COLLECTED("分类已收藏", "-500"),
    LINK_CATEGORY_COLLECTED("分类中已存在该链接", "-501");

    private final String message;
    private final String code;

    CodeEnum(String message, String code) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getResultMsg() {
        return message;
    }

    @Override
    public String getResultCode() {
        return code;
    }
}