package kr.taeu.SpringSecurityPractice.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

/*
 * 리소스 서버에 대한 설정
 * 어플리케이션(이용자)이 access Token을 부여 받은 후 인증 요청(authenticated requests)을 핸들링한다.
 * 인증 요청->리소스 자원에 대한 인증된 요청(ex: 유저정보..)
 * AccessToken에 대한 검증
 * Scope에 대한 검증
 * Error codes 및 비인가 접근에 대한 처리
 * https://www.oauth.com/oauth2-servers/the-resource-server/
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		super.configure(resources);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.anonymous()
				.disable()
			.authorizeRequests()
				.antMatchers("/member/resource/**").authenticated()
				.and()
			.exceptionHandling()
				.accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
	
}
