package com.thenextmediagroup.dsod.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * 复写LoginUrlAuthenticationEntryPoint类， 重写commence方法
 * 
 * @author Kevin.Yin
 *
 */
public class LoginUrlAuthenticationEntryPointExtention extends LoginUrlAuthenticationEntryPoint {

	public LoginUrlAuthenticationEntryPointExtention(String loginFormUrl) {
		super(loginFormUrl);
	}

	/**
	 * 重写此方法，返回json。内部可再自己加工
	 */
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer();
		sb.append("{\"code\":\"1014\",\"msg\":\"");
		sb.append("access token is null");
		sb.append("\"}");
		out.write(sb.toString());
		out.flush();
		out.close();
		System.out.println(request.getHeaderNames());
	}

}
