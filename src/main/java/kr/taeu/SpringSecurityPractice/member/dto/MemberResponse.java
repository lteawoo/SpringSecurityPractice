package kr.taeu.SpringSecurityPractice.member.dto;

import kr.taeu.SpringSecurityPractice.member.domain.Member;
import kr.taeu.SpringSecurityPractice.member.domain.model.Email;
import kr.taeu.SpringSecurityPractice.member.domain.model.Password;
import kr.taeu.SpringSecurityPractice.member.domain.model.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponse {
	private Email email;
	
	private Password password;
	
	private Role role;
	
	public MemberResponse(final Member member) {
		this.email = member.getEmail();
		this.password = member.getPassword();
		this.role = member.getRole();
	}
}
