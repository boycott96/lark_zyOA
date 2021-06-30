package lch.bean.common;

public enum ApiEnum {

    /**
     * header: {
     *     Content-Type: application/json; charset=utf-8
     * }
     * requestBody: {
     *     app_id: string
     *     app_secret: string
     * }
     */
    APP_ACCESS_TOKEN_URI("获取 app_access_token", "https://open.feishu.cn/open-apis/auth/v3/app_access_token/internal/"),

    /**
     * header: {
     *     Authorization: Bearer access_token
     *     Content-Type: application/json; charset=utf-8
     * }
     */
    LOGIN_USER("获取用户信息", "https://open.feishu.cn/open-apis/authen/v1/access_token"),
    ;


    public String name;
    public String uri;

    ApiEnum(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }
}
