package lch.app.service;

import lch.bean.common.ResultBody;

import javax.servlet.http.HttpSession;

public interface SystemService {
    ResultBody login(HttpSession session);
}
