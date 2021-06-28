package lch.quartz.api;

import lch.config.httpclient.HttpClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
