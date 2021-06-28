package lch.quartz.service;

import lch.quartz.api.ZyApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class QuartzService {

    @Value("${source-url}")
    private String sourceUrl;

    private final ZyApi zyApi;

    @Autowired
    public QuartzService(ZyApi zyApi) {
        this.zyApi = zyApi;
    }

    /**
     * 定时发送心跳
     */
    @Scheduled(fixedRate = 60000)
    public void timeSendBeat() {
        zyApi.sendBeatLink(sourceUrl + (int) (Math.random() * 10000));
    }
}
