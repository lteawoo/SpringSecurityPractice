package kr.taeu.SpringSecurityPractice.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.taeu.SpringSecurityPractice.member.domain.model.Role;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	/*
	 * 스프링 시큐리티 암호화 알고리즘 Bean 등록
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		//return PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return NoOpPasswordEncoder.getInstance();
	}
	
	/*
	 * 스프링 시큐리티 룰 설정
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("HttpSecurity config...");
		http.authorizeRequests()
				//페이지 권한 설정
				.antMatchers("/api/signup").permitAll()
				.antMatchers("/oauth/**").permitAll()
				.antMatchers("/api/callback").permitAll()
				.anyRequest().hasAuthority(Role.MEMBER.name())
			.and()
				.formLogin()
			.and()
				.httpBasic()
					.disable()
				.csrf()
					.disable();
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
