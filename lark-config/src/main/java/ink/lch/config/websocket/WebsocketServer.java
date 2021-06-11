package ink.lch.config.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/websocket/{id}")
@Component
@Slf4j
public class WebsocketServer {

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
    private static Map<String, Session> sessionPools = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "id") String id) {
        sessionPools.put(id, session);
        log.info("websocket已连接");
        try {
            sendMessage(session, "欢迎id为：" + id + "加入连接！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(@PathParam(value = "id") String id) {
        sessionPools.remove(id);
        System.out.println(id + "断开webSocket连接！当前人数为");
        log.info("id:" + id + "websocket已关闭");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public void sendMessage(Session session, String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }


    /**
     * 发送自定义消息
     */
    public void sendInfo(String id, String message) {
        Session session = sessionPools.get(id);
        try {
            sendMessage(session, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
