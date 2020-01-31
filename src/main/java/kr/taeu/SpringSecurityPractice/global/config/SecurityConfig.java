package kr.taeu.SpringSecurityPractice.global.config;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import kr.taeu.SpringSecurityPractice.member.domain.model.Role;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableConfigurationProperties(OAuth2ClientProperties.class)
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
	 * 인증되지 않은 요청에 대한 행동을 정의 하는 Bean 등록
	 */
//	@Bean
//	public AuthenticationEntryPoint authenticationEntryPoint() {
//		return new RestAuthenticationEntryPoint();
//	}
	
	/*
	 * Client Repository 등록
	 */
	@Bean
	public ClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties clientProperties) {
		for(String s : clientProperties.getRegistration().keySet()) {
			log.info("key : " + s);
		}
		List<ClientRegistration> registrations = clientProperties.getRegistration().keySet().stream()
					.map(clientName -> getRegistration(clientProperties, clientName))
					.filter(Objects::nonNull)
					.collect(Collectors.toList());
		return new InMemoryClientRegistrationRepository(registrations);
	}

	private ClientRegistration getRegistration(OAuth2ClientProperties clientProperties, String clientName) {
		ClientRegistration clientRegistration = null;

		switch(clientName) {
			case "google" : 
				OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get(clientName);
				clientRegistration = CommonOAuth2Provider.GOOGLE.getBuilder(clientName)
						.clientId(registration.getClientId())
						.clientSecret(registration.getClientSecret())
						.redirectUriTemplate(registration.getRedirectUri())
						.scope("email", "profile")
						.build();
				break;
		}
		
		return clientRegistration;
	}
	
	/*
	 * 스프링 시큐리티 룰 설정
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("HttpSecurity config...");
		http.authorizeRequests()
				//페이지 권한 설정
				.antMatchers("/oauth/**").permitAll()
				.antMatchers("/member/", "/member/signup", "/member/signin").permitAll()
				.anyRequest().hasAuthority(Role.MEMBER.name())
			.and()
				.oauth2Login()
				//.clientRegistrationRepository(clientRegistrationRepository())
				.loginPage("/member/signin")
//				.loginProcessingUrl("/member/signin")
//				.defaultSuccessUrl("/member/status")
//				.failureUrl("/member/signin")
			.and()
				.csrf()
					.disable();
			//.and()
			//	.formLogin()
			//		.loginPage("/member/signin")
			//		.loginProcessingUrl("/member/signin")
			//		.defaultSuccessUrl("/member/status")

			//.exceptionHandling()
			//	.authenticationEntryPoint(authenticationEntryPoint());
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
                "/h2-console/**",
                "/js/**");
	}
}
