package lch.app.service.impl;

import lch.app.service.SystemService;
import lch.bean.common.ResultBody;
import lch.config.util.StringHandlerUtil;
import lch.quartz.api.LarkApi;
import lch.quartz.api.ZyApi;
import lch.redis.common.RedisEnum;
import lch.redis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@Slf4j
public class SystemServiceImpl implements SystemService {

    private final ZyApi zyApi;
    private final RedisUtil redisUtil;
    private final LarkApi larkApi;


    @Autowired
    public SystemServiceImpl(ZyApi zyApi, RedisUtil redisUtil, LarkApi larkApi) {
        this.zyApi = zyApi;
        this.redisUtil = redisUtil;
        this.larkApi = larkApi;
    }

    @Override
    public ResultBody login(HttpSession session) {
        ResultBody resultBody = new ResultBody();
        // 获取session中的票据
        Object ticket = session.getAttribute("ticket");
        if(ticket == null) {
            resultBody.setErrorCode();
        } else {
            // 通过解密的方式，将登录名拿到
            String employNo = StringHandlerUtil.decode(ticket.toString());

            Object appAccess = redisUtil.get(RedisEnum.APP_ACCESS.name);
            // 打印redis数据
            log.info(RedisEnum.APP_ACCESS.name, appAccess);
            if(appAccess == null) {
                // 获取记录的refresh token值
                Object refresh = redisUtil.get(RedisEnum.OLD_APP_ACCESS.name);
                // 如果没有记录，则进行初次申请凭证.
                if(refresh == null) {
                    ResultBody appAccessTokenResult = larkApi.getAppAccessToken();
                    String appAccessToken = appAccessTokenResult.getResult().toString();

                    // 通过appAccessToken，获取当前的登录用户信息

                } else {
                    // 进行刷新凭证 todo
                }

            }

            // 通过redis，获取到对应app_access_token
            redisUtil.get(RedisEnum.OLD_APP_ACCESS.name);

            redisUtil.hasKey(RedisEnum.APP_ACCESS.name);

            // todo
        }

        return resultBody;
    }
}
