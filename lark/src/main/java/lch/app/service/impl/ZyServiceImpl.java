package lch.app.service.impl;

import lch.quartz.api.ZyApi;
import lch.app.service.ZyService;
import lch.bean.common.ResultBody;
import lch.config.util.StringHandlerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
@Slf4j
public class ZyServiceImpl implements ZyService {

    private final ZyApi zyApi;

    @Autowired
    public ZyServiceImpl(ZyApi zyApi) {
        this.zyApi = zyApi;
    }

    @Override
    public ResultBody ssoLogin(String name, HttpServletResponse response) {
        Cookie cookie = new Cookie("login", name);
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
        String ticket = StringHandlerUtil.encode(name);
        return zyApi.sendSsoLink(ticket);
    }
}
