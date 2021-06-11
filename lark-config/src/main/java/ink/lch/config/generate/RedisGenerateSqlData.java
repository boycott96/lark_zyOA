package ink.lch.config.generate;

import ink.lch.redis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

//@Component
@Slf4j
public class RedisGenerateSqlData implements InitializingBean, ServletContextAware {

    private final RedisUtil redisUtil;

    @Autowired
    public RedisGenerateSqlData(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    private void setRedisWithUser() {
        log.info("开始转载数据库中的用户数据到 REDIS缓存");
        // 获取已经注册完成的所有数据
        log.info("用户数据转载完成");
    }

    @Override
    public void afterPropertiesSet() {

    }

    @Override
    public void setServletContext(ServletContext servletContext) {
//        this.setRedisWithUser();
    }
}
