package ir.mywallet.config;


import ir.mywallet.services.jwt.JWTService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtRequestFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
	@Override
	public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String token = httpRequest.getHeader("Authorization");
		String userId=httpRequest.getHeader("userId");
		
		JWTService jwtService=new JWTService();
		if (token == null || !jwtService.checkValidToken(token,userId)) {
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or missing token");
		}
		chain.doFilter(request, response);
	}
	
	
	@Override
	public void destroy() {
	}
}
