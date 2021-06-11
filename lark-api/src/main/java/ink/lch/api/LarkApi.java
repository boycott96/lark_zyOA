package ink.lch.api;

import com.alibaba.fastjson.JSONObject;
import ink.lch.common.ResultBody;
import ink.lch.config.httpclient.HttpClientService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class LarkApi {

    @Value("${lark.app_id}")
    private String appId;

    @Value("${lark.app_secret}")
    private String appSecret;

    @Value("${lark.open_id}")
    private String openId;

    private final HttpClientService httpClientService;

    @Autowired
    public LarkApi(HttpClientService httpClientService) {
        this.httpClientService = httpClientService;
    }
}
