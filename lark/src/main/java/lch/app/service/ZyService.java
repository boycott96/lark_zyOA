package lch.app.service;

import lch.bean.common.ResultBody;

import javax.servlet.http.HttpServletResponse;

public interface ZyService {
    ResultBody ssoLogin(String name, HttpServletResponse response);
}
