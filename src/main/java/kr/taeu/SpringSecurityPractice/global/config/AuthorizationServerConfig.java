package kr.taeu.SpringSecurityPractice.global.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * 인가 설정(AuthorizationServerConfig)을 담당한다.
 * Client 관련 설정
 * 토큰 관련 설정 등..
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	private final DataSource dataSource;
	//private final PasswordEncoder passwordEncoder;
	private final UserDetailsService memberService;
	
//	@Bean
//	public ClientDetailsService clientDetailsService(DataSource dataSource) {
//		return new JdbcClientDetailsService(dataSource);
//	}
	
	/*
	 * jwt 인증키 설정
	 * 이 키는 Auth server와 Resource server가 동일해야함.
	 */
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("taeuKey");
		return converter;
	}
	
	/*
	 * 토큰 스토어 JwtTokenStore 사용
	 * jwt를 사용하므로 토큰 정보를 토큰 자체에서 관리함 -> DB에 토큰 정보관리 불필요
	 */
	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
	
	@Bean
	public JdbcApprovalStore approvalStore() {
		return new JdbcApprovalStore(dataSource);
	}

	/*
     * Client 설정
     * 1) InMemory - 기본 구현체 InMemoryClientDetailsService(Map에 클라이언트를 저장)
     * 2) JDBC - 기본 구현체 JdbcClientDetailsService(JdbcTemplate를 이용한 DB이용)
     * 3) ClientDetailsService 설정
     */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		log.info("AuthorizationServerConfig configure...");
		clients.jdbc(this.dataSource);
		/*
		clients.inMemory()
				.withClient("taeuClient")	// 인가서버에 등록할 client
				.secret("taeuSecret")	// 암호키
				.redirectUris("http://localhost:8080/oauth2/code/taeu") //리다이렉트 URI, 인가 요청 시의 URI와 매칭되어야한다.
				.authorizedGrantTypes("authorization_code") //권한 부여 방식
				.scopes("profile") //발급된 AccessToken으로 접근할 수 있는 리소스의 범위.
				.accessTokenValiditySeconds(30000); // 토큰의 유효시간(초)
		 */
	}
	
	/*
	 * Authorization과 토큰 엔트포인트, 토큰 서비스를 정의
	 * 프레임워크가 제공하는 URL 경로는 /oauth/authorize (인가 엔드포인트), 
	 * /oauth/token (토큰 엔드포인트), 
	 * /oauth/confirm_access (사용자가 허가의 승인을 확인하는 POST 요청), 
	 * /oauth/error (인가 서버에서 에러를 보여줄 때 사용), 
	 * /oauth/check_token (액세스 토큰을 복호화 하기 위해 리소스 서버에서 사용), 
	 * /oauth/token_key (JWT 토큰을 사용하는 경우 토큰 검증을 위한 공개키를 노출)가 있다.
	 * https://chanwookpark.github.io/spring/oauth/2016/01/26/oauth2-spring-dev-guide/
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.accessTokenConverter(accessTokenConverter())
			.userDetailsService(memberService)
			.approvalStore(approvalStore());
			//.pathMapping("/oauth/authorize", "/oauth2/authorize")
			//.pathMapping("/oauth/token", "/oauth2/token");
	}
	
	/*
	 * 토큰 endpoints에 대한 보악 제약을 정의
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security
			.tokenKeyAccess("permitAll()") // allow check token
			.checkTokenAccess("isAuthenticated()");
	}
}
