package kr.taeu.SpringSecurityPractice.global.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.taeu.SpringSecurityPractice.member.domain.model.Email;
import kr.taeu.SpringSecurityPractice.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationProviderImpl implements AuthenticationProvider{
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.info("authenticate...");
		/*
		 * principal credentials로 이루어진 토큰
		 * principal : Email
		 * crendentials : Password
		 */
		
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
		
		Email email = new Email(token.getName());
//		memberService.
		
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return false;
	}

}
