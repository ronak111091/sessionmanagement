package sw.arch.sessionmanagement.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

public class SessionMangamentFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletResponse httpResp = (HttpServletResponse) response;
        HttpServletRequest httpReq = (HttpServletRequest) request;
        long currTime = System.currentTimeMillis();
        long expiryTime = currTime + 60 * 1000;
        Cookie cookie = new Cookie("serverTime", "" + currTime);
        cookie.setPath("/");
        httpResp.addCookie(cookie);
//        if (httpReq.getRemoteUser() != null) {
            cookie = new Cookie("sessionExpiry", "" + expiryTime);
//        } else {
//            cookie = new Cookie("sessionExpiry", "" + currTime);
//        }
        cookie.setPath("/");
        httpResp.addCookie(cookie);
        chain.doFilter(request, response);
	}
	
	
}
