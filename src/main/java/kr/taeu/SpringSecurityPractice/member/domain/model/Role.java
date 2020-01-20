package kr.taeu.SpringSecurityPractice.member.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {
	@Column(name="ROLE", nullable=false)
	@NotNull
	private RoleType value;
	
	public Role(RoleType value) {
		this.value = value;
	}
}
