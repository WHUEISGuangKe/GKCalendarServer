package com.guangke.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;
import com.sun.corba.se.impl.util.Version;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter(dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE,
		DispatcherType.ERROR }, urlPatterns = { "/*" })
public class LoginFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public LoginFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		String uri = request.getRequestURI();
		if (uri.indexOf("login") >= 0 || uri.indexOf("register") >= 0 || uri.indexOf("error") >= 0) {

		} else {
			String token = request.getParameter("token");
			String username = request.getParameter("username");
			boolean flag = false;
			if (token != null && username != null && !token.isEmpty() && !username.isEmpty()) {
				if (VersionController.TokenMap.get(username) == null && !VersionController.TokenMap.get(username).equals(token))
					flag = true;
			} else {
				flag = true;
			}

			if (flag) {
				JSONObject jsonObject = new JSONObject();
				try {

					jsonObject.put("ret_code", 200);
					jsonObject.put("message", "请先登录");

				} catch (Exception e) {
					throw new RuntimeException();
				}

				try {
					response.setContentType("text/html;charset=utf-8");
					PrintWriter out = response.getWriter();

					String jsonString = jsonObject.toString();
					out.println(jsonString);
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
