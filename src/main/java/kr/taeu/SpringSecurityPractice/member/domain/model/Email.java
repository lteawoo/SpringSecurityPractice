package kr.taeu.SpringSecurityPractice.member.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {
	@javax.validation.constraints.Email
	@Column(name = "EMAIL", length = 100, unique = true, nullable = false, updatable = false)
	@NotEmpty
	private String value;
	
	public Email(final String value) {
		this.value = value;
	}
}
