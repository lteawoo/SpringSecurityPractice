package kr.taeu.SpringSecurityPractice.member.dto;

import javax.validation.Valid;

import kr.taeu.SpringSecurityPractice.member.domain.Member;
import kr.taeu.SpringSecurityPractice.member.domain.model.Email;
import kr.taeu.SpringSecurityPractice.member.domain.model.Password;
import kr.taeu.SpringSecurityPractice.member.domain.model.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequest {
	@Valid
	private Email email;
	
	@Valid
	private Password password;
	
	@Valid
	private Role role;
	
	public SignUpRequest(@Valid Email email, @Valid Password password, @Valid Role role) {
		this.email = email;
		this.password = password;
		this.role = role;
	}
	
	public Member toEntity() {
		return Member.builder()
				.email(this.email)
				.password(this.password)
				.role(this.role)
				.build();
	}
}
