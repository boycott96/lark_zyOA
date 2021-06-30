package lch.app.controller;

import lch.quartz.api.LarkApi;
import lch.config.websocket.WebsocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 对所有的请求，进行一个接收
 */
@RestController
@RequestMapping("/")
@Slf4j
public class AnyController {

    private final WebsocketServer websocketServer;

    private final LarkApi larkApi;

    @Autowired
    public AnyController(WebsocketServer websocketServer, LarkApi larkApi) {
        this.websocketServer = websocketServer;
        this.larkApi = larkApi;
    }

    /**
     * 所有请求的通道，对致远的回馈进行处理，进行处理
     *
     * @return
     */
    @RequestMapping()
    public Object accessRequest(HttpServletRequest request, HttpServletResponse response) {
        return new Object();
    }
}
