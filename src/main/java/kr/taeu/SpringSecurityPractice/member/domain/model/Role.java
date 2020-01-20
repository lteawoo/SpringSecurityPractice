package kr.taeu.SpringSecurityPractice.member.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;

@Embeddable
@Getter
public enum Role {
	MEMBER("Member"),
	ADMIN("admin");
	
	@Column(name="ROLE", nullable=false)
	@NotNull
	private final String value;
	
	private Role(String value) {
		this.value = value;
	}
}
