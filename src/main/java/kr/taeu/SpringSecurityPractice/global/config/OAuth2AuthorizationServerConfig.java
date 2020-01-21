package kr.taeu.SpringSecurityPractice.global.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	private final DataSource dataSource;
	private final PasswordEncoder passwordEncoder;

	/*
     * Client에 대한 인증 처리를 위한 설정
     * 1) InMemory - 기본 구현체 InMemoryClientDetailsService(Map에 클라이언트를 저장)
     * 2) JDBC - 기본 구현체 JdbcClientDetailsService(JdbcTemplate를 이용한 DB이용)
     * 3) ClientDetailsService 설정
     */
	
//	@Bean
//	public JwtTokenStore tokenStore() {
//		return new JwtTokenStore();
//	}
	
//	@Bean
//	public JwtAccessTokenConverter accessTokenConverter() {
//		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//		converter.setSigningKey(key);
//	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		log.info("OAuth2AuthorizationServerConfig configure...");
		clients.jdbc(this.dataSource)
			.passwordEncoder(passwordEncoder);
			/*.withClient("testClientId")
			.secret("testSecret")
			//.redirectUris("/oauth2/callback") //인증 성공 후 리다이렉트 URI
			.authorizedGrantTypes("authorization_code") //인증 방식
			.scopes("read", "write") //발급된 AccessToken으로 접근할 수 있는 리소스의 범위. 일단 테스트로 read write를 세팅
			.accessTokenValiditySeconds(30000); // 토큰의 유효시간(초)
*/	}
}
