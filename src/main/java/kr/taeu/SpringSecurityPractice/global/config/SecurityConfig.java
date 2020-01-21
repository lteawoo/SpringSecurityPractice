package kr.taeu.SpringSecurityPractice.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	/*
	 * 스프링 시큐리티 암호화 알고리즘 Bean 등록
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	/*
	 * 스프링 시큐리티 룰 설정
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("HttpSecurity config...");
		http.authorizeRequests()
			//페이지 권한 설정
			.antMatchers("/api/status").permitAll()
			.antMatchers("/api/signin", "/api/signup").permitAll()
			.anyRequest().hasRole("MEMBER")
			.and()
				.formLogin()
			.and()
				.httpBasic()
					.disable()
				.csrf()
					.disable()
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	/*
	 * 스프링 시큐리티 룰 무시하는 URL
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                "/h2-console/**");
	}
}
