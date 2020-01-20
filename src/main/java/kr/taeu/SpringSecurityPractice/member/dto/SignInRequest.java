package kr.taeu.SpringSecurityPractice.member.dto;

import javax.validation.Valid;

import kr.taeu.SpringSecurityPractice.member.domain.model.Email;
import kr.taeu.SpringSecurityPractice.member.domain.model.Password;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInRequest {
	@Valid
	private Email email;
	
	@Valid
	private Password password;
	
	public SignInRequest(@Valid Email email, @Valid Password password) {
		this.email = email;
		this.password = password;
	}
}
