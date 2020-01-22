package kr.taeu.SpringSecurityPractice.member.domain.model;

import lombok.Getter;

@Getter
public enum Role {
	MEMBER("MEMBER"),
	ADMIN("ADMIN");

	private final String value;
	
	Role(final String value) {
		this.value = value;
	}
}
