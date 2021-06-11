package ink.lch.redis.common;

public enum RedisEnum {
    MYSQL_EMAIL("exist_email"),
    MYSQL_NAME("exist_name");
    private final String name;

    RedisEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
