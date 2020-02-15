package kr.taeu.SpringSecurityPractice.member.domain.model;

import lombok.Getter;

@Getter
public enum SocialType {
	GOOGLE("GOOGLE");
	
	SocialType(String value) {
		this.value = value;
	}
	
	private final String value;
}
