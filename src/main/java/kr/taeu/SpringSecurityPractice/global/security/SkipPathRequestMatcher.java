package kr.taeu.SpringSecurityPractice.global.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class SkipPathRequestMatcher implements RequestMatcher{
	
	private OrRequestMatcher skipRequestMatcher;
	
	public SkipPathRequestMatcher(String...skipPathList ) {
		List<RequestMatcher> requestMatcherList = new ArrayList<>();
		
		for(String pattern : skipPathList) {
			requestMatcherList.add(new AntPathRequestMatcher(pattern));
		}
		
		this.skipRequestMatcher = new OrRequestMatcher(requestMatcherList);
	}

	@Override
	public boolean matches(HttpServletRequest request) {
		return !skipRequestMatcher.matches(request);
	}

}
