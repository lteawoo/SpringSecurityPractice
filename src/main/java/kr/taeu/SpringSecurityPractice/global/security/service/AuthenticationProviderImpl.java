package kr.taeu.SpringSecurityPractice.global.security.service;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.taeu.SpringSecurityPractice.member.domain.model.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * AuthenticationProvider : 실제 인증을 처리하는 곳 인증처리 후 Authentication을 AuthenticationManager에게 반환
 * AuthenticaionManager(ProviderManager로 구현) -> AuthenticationProvider(AuthenticationProviderImpl 구현)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationProviderImpl implements AuthenticationProvider{
	private final UserDetailsService userDetailsServiceImpl;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.info("authenticate...");
		/*
		 * principal credentials로 이루어진 토큰
		 * principal : Email
		 * credentials : Password
		 */
		
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
		
		Email email = new Email(token.getName());
		UserDetails user = userDetailsServiceImpl.loadUserByUsername(email.getValue());
		String password = user.getPassword();
		
		if(!passwordEncoder.matches(String.valueOf(token.getCredentials()), password)) {
			throw new BadCredentialsException("Invalid Passsword");
		}
		
		return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class
				.isAssignableFrom(authentication));
	}

}
