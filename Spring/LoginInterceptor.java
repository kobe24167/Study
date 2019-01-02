package com.test;

import java.lang.reflect.Method;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
			throws Exception {
		if (o instanceof HandlerMethod) {
			// 检查是否有@LoginRequired登录注解
			HandlerMethod handlerMethod = (HandlerMethod) o;
			Method method = handlerMethod.getMethod();
			LoginRequired annotation = method.getAnnotation(LoginRequired.class);
			// 没有直接放行，有的话进行相应的验证
			if (annotation != null) {
				// 具体验证逻辑略
			}
		}
		String currentURL = httpServletRequest.getRequestURI();
		HttpSession session = httpServletRequest.getSession(false);
		Cookie[] cs = httpServletRequest.getCookies();

		if (session == null || session.getAttribute("onlineUser") == null) {
			if (cs != null) {
				for (Cookie c : cs) {
					if (c.getName().equals("autoLogin")) {// 如果存在自动登录的cookie
						String value = c.getValue();// 用户名称
						httpServletRequest.getSession().setAttribute("onlineUser", value);
					}
					break;
				}
			}
			if (!currentURL.contains("/pages")) {
				return true;
			}
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath());
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object o, Exception e) throws Exception {

	}
}
