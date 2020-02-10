package kr.taeu.SpringSecurityPractice.member.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import kr.taeu.SpringSecurityPractice.member.dao.MemberRepository;
import kr.taeu.SpringSecurityPractice.member.domain.Member;
import kr.taeu.SpringSecurityPractice.member.domain.model.Email;
import kr.taeu.SpringSecurityPractice.member.domain.model.Password;
import kr.taeu.SpringSecurityPractice.member.domain.model.Role;
import kr.taeu.SpringSecurityPractice.member.domain.model.SignUpType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oauth2User = super.loadUser(userRequest);
		
		updateMember(oauth2User);
		
		return super.loadUser(userRequest);
	}

	private void updateMember(OAuth2User oauth2User) {
		Email email = new Email(oauth2User.getAttribute("email"));
		
		Optional<Member> optMember = this.memberRepository.findByEmail(email);
		Member member = optMember.orElseGet(() -> {
			Member newMember = Member.builder()
					.email(email)
					.signUpType(SignUpType.GOOGLE)
					.password(new Password(passwordEncoder.encode("12345")))
					.role(Role.MEMBER)
					.build();
			
			return newMember;
		});
		
		this.memberRepository.save(member);
	}
}
