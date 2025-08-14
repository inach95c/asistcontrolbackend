package com.sistema.examenes.configuraciones;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint  {
 
	
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Usuario no autorizado");
		
	}
	
//mio en lo del loco no esta
/*	@Override
	public void commence(javax.servlet.http.HttpServletRequest arg0, javax.servlet.http.HttpServletResponse arg1,
			AuthenticationException arg2) throws IOException, javax.servlet.ServletException {
		// TODO Auto-generated method stub
		
	}
*/
}
