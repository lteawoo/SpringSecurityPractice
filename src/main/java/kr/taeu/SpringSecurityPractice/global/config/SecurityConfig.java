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
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import kr.taeu.SpringSecurityPractice.global.security.oauth2.client.CustomOAuth2Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableConfigurationProperties(OAuth2ClientProperties.class)
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final OAuth2UserService<OAuth2UserRequest, OAuth2User> customOAuth2UserSerivce;
	
	/*
	 * 인증되지 않은 요청에 대한 행동을 정의 하는 Bean 등록
	 */
//	@Bean
//	public AuthenticationEntryPoint authenticationEntryPoint() {
//		return new RestAuthenticationEntryPoint();
//	}
	
	/*
	 * Client Repository 등록
	 * 내부적으로 client 정보를 가지고 있어야하는데 inmemory로 관리하자.
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
		OAuth2ClientProperties.Registration registration = null;
		switch(clientName) {
			case "google" : 
				registration = clientProperties.getRegistration().get(clientName);
				clientRegistration = CommonOAuth2Provider.GOOGLE.getBuilder(clientName)
						.clientId(registration.getClientId())
						.clientSecret(registration.getClientSecret())
						.redirectUriTemplate(registration.getRedirectUri()) //oauth authorziation server의 redirection uri와 일치해야함.
						.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
						.scope("email", "profile")
						.build();
				break;
			case "taeu" :
				registration = clientProperties.getRegistration().get(clientName);
				clientRegistration = CustomOAuth2Provider.TAEU.getBuilder(clientName)
						.clientId(registration.getClientId())
						.clientSecret(registration.getClientSecret())
						.redirectUriTemplate(registration.getRedirectUri())
						.authorizationGrantType(new AuthorizationGrantType(registration.getAuthorizationGrantType()))
						.scope("profile")
						.build();
				break;
		}
		
		return clientRegistration;
	}
	
	@Bean
	public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
		return new HttpSessionOAuth2AuthorizationRequestRepository();
	}
	
	@Bean
	public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
		//return new NimbusAuthorizationCodeTokenResponseClient();
		return new DefaultAuthorizationCodeTokenResponseClient();
	}
	
	@Bean
	public OAuth2AuthorizedClientManager authorizedClientManager(
	        ClientRegistrationRepository clientRegistrationRepository,
	        OAuth2AuthorizedClientRepository authorizedClientRepository) {

	    OAuth2AuthorizedClientProvider authorizedClientProvider =
	            OAuth2AuthorizedClientProviderBuilder.builder()
	                    .authorizationCode()
	                    .refreshToken()
	                    .clientCredentials()
	                    .password()
	                    .build();

	    DefaultOAuth2AuthorizedClientManager authorizedClientManager =
	            new DefaultOAuth2AuthorizedClientManager(
	                    clientRegistrationRepository, authorizedClientRepository);
	    authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

	    return authorizedClientManager;
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
				//.antMatchers("/member/signup", "/member/signin/**").permitAll()
				.antMatchers("/member/signup", "/member/signin/**").anonymous() //가입, 로그인은 익명만 가능
				.anyRequest().authenticated() // 모든요청은 인가되어야함.
			.and()
				.exceptionHandling()
					.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/member/signin"))
			.and()
				.logout()
					.logoutUrl("/member/signout")
					.clearAuthentication(true)
			.and()
				.formLogin()
					.disable()
//					.loginPage("/member/signin")
//					.loginProcessingUrl("/member/signin")
//					.defaultSuccessUrl("/member/signsuccess")
				.oauth2Client()
					.authorizationCodeGrant()
				.and()
			.and()
				.oauth2Login()
					.loginPage("/member/signin")
//					.loginProcessingUrl("/member/signin/oauth2/code/*")
					.authorizationEndpoint()
						.baseUri("/member/signin/oauth2/authorization") //OAuth2 인가서버들의 baseuri설정 default:/login/oauth2/authorization/
						.authorizationRequestRepository(this.authorizationRequestRepository())
				.and()
					.redirectionEndpoint() //redirection endpoint 설정 default: /login/oauth2/code/*
						.baseUri("/member/signin/oauth2/code/*")
				.and()
					.tokenEndpoint()
						.accessTokenResponseClient(this.accessTokenResponseClient()) //tokenEndpoint(code로 token 받아오는..)
				.and()
					.userInfoEndpoint()	//token으로  userinfo 받아옴
						.userService(this.customOAuth2UserSerivce)
				.and()
					.defaultSuccessUrl("/member/signsuccess")
			.and()	
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
                "/h2-console/**",
                "/js/**");
	}
}
