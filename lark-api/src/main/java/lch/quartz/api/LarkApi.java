package lch.quartz.api;

import com.alibaba.fastjson.JSONObject;
import lch.bean.common.ApiEnum;
import lch.bean.common.ResultBody;
import lch.config.httpclient.HttpClientService;
import lch.redis.common.RedisEnum;
import lch.redis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class LarkApi {

    @Value("${lark.app_id}")
    private String appId;

    @Value("${lark.app_secret}")
    private String appSecret;

    private final HttpClientService httpClientService;
    private final RedisUtil redisUtil;

    @Autowired
    public LarkApi(HttpClientService httpClientService, RedisUtil redisUtil) {
        this.httpClientService = httpClientService;
        this.redisUtil = redisUtil;
    }

    /**
     * 获取应用的访问凭证
     *
     * @return ResultBody
     */
    public ResultBody getAppAccessToken() {
        ResultBody resultBody = new ResultBody();
        Header[] headers = new Header[]{new BasicHeader("Content-Type", "application/json; charset=utf-8")};
        Map<String, Object> map = new HashMap<>();
        map.put("app_id", appId);
        map.put("app_secret", appSecret);
        try {
            CloseableHttpResponse closeableHttpResponse = httpClientService.doGet(ApiEnum.APP_ACCESS_TOKEN_URI.uri, headers, map);
            if (closeableHttpResponse != null) {
                // 获取反馈的信息
                String str = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
                JSONObject result = JSONObject.parseObject(str);
                int code = Integer.parseInt(result.get("code").toString());

                // 对反馈的信息进行判断
                if (code == 0) {
                    // 获取app_access_token或它的过期时间
                    String app_access_token = result.get("app_access_token").toString();
                    int expireTime = Integer.parseInt(result.get("expire").toString());
                    // 存储app_access用于刷新token
                    redisUtil.set(RedisEnum.OLD_APP_ACCESS.name, app_access_token);
                    // 存储app_access的过期时效性
                    redisUtil.set(RedisEnum.APP_ACCESS.name, app_access_token, expireTime);
                    resultBody.setResult(app_access_token);
                } else {
                    resultBody.setErrorCode();
                }
            } else {
                resultBody.setErrorCode();
            }
        } catch (URISyntaxException | IOException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }

        return resultBody;
    }

    /**
     * 获取当前的登录用户
     *
     * @param token 应用访问凭证
     * @param code  授权码
     * @return resultBody
     */
    public ResultBody getLoginUserInfo(String token, String code) {
        ResultBody resultBody = new ResultBody();
        // 设置头部信息和参数信息
        Header[] headers = new Header[]{
                new BasicHeader("Authorization", "Bearer " + token),
                new BasicHeader("Content-Type", "application/json; charset=utf-8")
        };
        Map<String, Object> map = new HashMap<>();
        map.put("grant_type", "authorization_code");
        map.put("code", code);

        try {
            // 访问接口，获取数据
            CloseableHttpResponse closeableHttpResponse = httpClientService.doGet(ApiEnum.LOGIN_USER.uri, headers, map);
            if (closeableHttpResponse != null) {
                resultBody.setResult(EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8"));
            } else {
                resultBody.setErrorCode();
            }
        } catch (URISyntaxException | IOException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
        return null;
    }
}
