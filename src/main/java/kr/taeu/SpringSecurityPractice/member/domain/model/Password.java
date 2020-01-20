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
public class Password {
	@Column(name="PASSWORD", length = 100, nullable = false)
	@NotEmpty
	private String value;
	
	public Password(final String value) {
		this.value = value;
	}
}
