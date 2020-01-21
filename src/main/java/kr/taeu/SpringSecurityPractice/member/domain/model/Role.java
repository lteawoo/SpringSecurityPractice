package kr.taeu.SpringSecurityPractice.member.domain.model;

import lombok.Getter;

@Getter
public enum Role {
	MEMBER("Member"),
	ADMIN("Admin");

	private final String value;
	
	Role(final String value) {
		this.value = value;
	}
}
