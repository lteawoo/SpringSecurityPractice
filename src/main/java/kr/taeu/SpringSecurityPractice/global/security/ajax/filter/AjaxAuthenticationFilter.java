package kr.taeu.SpringSecurityPractice.global.security.ajax.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.taeu.SpringSecurityPractice.member.domain.Member;
import kr.taeu.SpringSecurityPractice.member.dto.SignInRequest;

public class AjaxAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public AjaxAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
		super(requiresAuthenticationRequestMatcher);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: " + request.getMethod());
		}
		SignInRequest signInRequest = new ObjectMapper().readValue(request.getReader(), SignInRequest.class);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(signInRequest.getEmail().getValue()
				, signInRequest.getPassword().getValue());
		return getAuthenticationManager().authenticate(authentication);
	}
}
