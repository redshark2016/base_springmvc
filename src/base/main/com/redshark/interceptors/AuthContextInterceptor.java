package com.redshark.interceptors;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 前台权限拦截
 * @author jianyue.yan
 *
 */
public class AuthContextInterceptor extends HandlerInterceptorAdapter
{
	private String text_html = "/loginPage";
	
	private String application_json="/ajaxLogin";
    
    public static final Long  FRESH_USERPLAT_INTERVAL = 10L * 60 * 1000;
    
    public static final String LAST_REFRESH_USERPLAT = "userPlatRefreshTime";
    
    
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler)
			throws ServletException, IOException
	{
		String contextPath = request.getContextPath();
        if ("/".equals(contextPath)) 
        {
            contextPath = "";
        }
        String requestUri = request.getRequestURI();
        System.out.println(requestUri);
        
		if(request.getSession() == null || request.getSession().getAttribute("user") == null)
		{
			String contentType = request.getContentType();
			if(contentType != null && contentType.indexOf("application/json") != -1)
			{
				request.getRequestDispatcher(application_json).forward(request, response);
				return false;
			}
			//response.sendRedirect(text_html);
			request.getRequestDispatcher(text_html).forward(request, response);
			return false;
		}
		return true;
	}
}
