package ink.lch.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ink.lch.api.LarkApi;
import ink.lch.common.CodeEnum;
import ink.lch.common.ResultBody;
import ink.lch.config.websocket.WebsocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 对所有的请求，进行一个接收
 */
@RestController
@RequestMapping("/")
@Slf4j
public class AnyController {

    private final String verification_type = "url_verification";

    private final WebsocketServer websocketServer;

    private final LarkApi larkApi;

    @Autowired
    public AnyController(WebsocketServer websocketServer, LarkApi larkApi) {
        this.websocketServer = websocketServer;
        this.larkApi = larkApi;
    }

    /**
     * 所有请求的通道，对不同的飞书反馈请求，进行处理
     *
     * @return
     */
    @RequestMapping()
    public Object accessRequest(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            // 将请求转化成输入流
            ServletInputStream inputStream = request.getInputStream();

            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String str; // 初始化
            StringBuilder value = new StringBuilder();
            while ((str = bufferedReader.readLine()) != null) {
                value.append(str);
            }
            log.info(value.toString());
            JSONObject jsonObject = JSONObject.parseObject(value.toString());
            // 1.飞书access——token，2.获取消息token的反馈
            if (jsonObject != null) {
                if (verification_type.equals(jsonObject.get("type"))) {
                    map.put("challenge", jsonObject.get("challenge"));
                } else if ("event_callback".equals(jsonObject.get("type"))) {
                    // 处理请求体
                    JSONObject event = jsonObject.getJSONObject("event");
                    // 判断是否是消息
                    if ("message".equals(event.get("type"))) {
                        // 判断内容是否为费用报销订阅
                    } else if ("approval_instance".equals(event.get("type")) || "approval_task".equals(event.get("type"))) {
                        // 判断是否是审批实例
                        Map<String, Object> textMap = new HashMap<>();
                        String status = event.get("status").toString();
                        StringBuilder result = new StringBuilder("审批状态: " + status);
                    }

                } else if (jsonObject.get("type") == null) {
                    // 获取消息卡片的数据
                    String openId = jsonObject.get("open_id").toString();
                    JSONObject actionValue = jsonObject.getJSONObject("action").getJSONObject("value");
                    if ("ok".equals(actionValue.get("status"))) {
                        // 获取消息卡片的同意反馈
                        this.websocketServer.sendInfo(openId, "OK");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return map;
    }
}
