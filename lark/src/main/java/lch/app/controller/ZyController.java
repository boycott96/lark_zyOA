package lch.app.controller;

import lch.app.service.ZyService;
import lch.bean.common.ResultBody;
import lch.config.util.StringHandlerUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 对致远OA的反馈，进行处理
 */
@RestController
@RequestMapping("/zy_local")
@Slf4j
public class ZyController {

    private final ZyService zyService;

    @Autowired
    public ZyController(ZyService zyService) {
        this.zyService = zyService;
    }

    @GetMapping(value = "/getNameByTicket")
    public Object getNo(String ticket) {
        String name = StringHandlerUtil.decode(ticket);
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        return map;
    }

    @PostMapping(value = "/ssoLogin")
    public ResultBody ssoLogin(@RequestBody Map<String, Object> map, HttpServletResponse response) {
        if(map.isEmpty() || StringUtils.isEmpty(map.get("name").toString())) {
            log.error("数据异常");
        }
        return zyService.ssoLogin(map.get("name").toString(), response);
    }

    @GetMapping(value = "/ssoLogout")
    public ResultBody ssoLogout(HttpServletRequest request, HttpServletResponse response) {
        return new ResultBody();
    }
}
