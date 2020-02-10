package kr.taeu.SpringSecurityPractice.member.domain.model;

import lombok.Getter;

@Getter
public enum SignUpType {
	GOOGLE("GOOGLE"),
	TAEU("TAEU");
	
	SignUpType(String value) {
		this.value = value;
	}
	
	private final String value;
}
