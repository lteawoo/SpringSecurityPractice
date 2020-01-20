package kr.taeu.SpringSecurityPractice.member.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {
	MEMBER("Member"),
	ADMIN("admin");
	
	private String value;
}
