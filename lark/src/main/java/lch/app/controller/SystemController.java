package lch.app.controller;

import lch.app.service.SystemService;
import lch.bean.common.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/system")
public class SystemController {

    private final SystemService systemService;

    @Autowired
    public SystemController(SystemService systemService) {
        this.systemService = systemService;
    }

    /**
     * 对session中的值，进行判断
     * @param session session值
     * @return 返回结果
     */
    @PostMapping("/login")
    public ResultBody login(HttpSession session) {
        return systemService.login(session);
    }
}
