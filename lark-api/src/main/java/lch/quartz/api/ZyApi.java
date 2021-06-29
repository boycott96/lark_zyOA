package lch.quartz.api;

import lch.bean.common.ResultBody;
import lch.config.httpclient.HttpClientService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class ZyApi {

    private final HttpClientService httpClientService;

    @Autowired
    public ZyApi(HttpClientService httpClientService) {
        this.httpClientService = httpClientService;
    }

    /**
     * 发送sso登录链接
     *
     * @param ticket 票据
     * @return 返回值
     */
    public ResultBody sendSsoLink(String ticket) {
        ResultBody resultBody = new ResultBody();
        String url = "http://jrhl.vaiwan.com/seeyon/login/sso?ticket=" + ticket + "&from=samplesso";
        try {
            CloseableHttpResponse closeableHttpResponse = httpClientService.doGet(url);
            if (closeableHttpResponse != null) {
                String str = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
                if (str.contains("already SSO") || str.contains("SSO OK")) {
                    resultBody.setResult(ticket);
                } else {
                    resultBody.setErrorCode();
                }
                log.info(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultBody;
    }

    /**
     * 定时发送心跳链接
     *
     * @param url 心跳链接
     * @return 返回值
     */
    public void sendBeatLink(String url) {
        try {
            CloseableHttpResponse closeableHttpResponse = httpClientService.doGet(url);
            if (closeableHttpResponse != null) {
                String str = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
                log.info(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
