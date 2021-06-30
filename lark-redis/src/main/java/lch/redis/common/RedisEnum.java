package lch.redis.common;

public enum RedisEnum {
    MYSQL_EMAIL("exist_email"),
    MYSQL_NAME("exist_name"),
    OLD_APP_ACCESS("old_app_access_token"),
    APP_ACCESS("app_access_token");

    public final String name;

    RedisEnum(String name) {
        this.name = name;
    }
}
