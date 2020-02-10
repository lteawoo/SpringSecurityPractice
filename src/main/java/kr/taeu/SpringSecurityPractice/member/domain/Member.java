package kr.taeu.SpringSecurityPractice.member.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import kr.taeu.SpringSecurityPractice.member.domain.model.Email;
import kr.taeu.SpringSecurityPractice.member.domain.model.Password;
import kr.taeu.SpringSecurityPractice.member.domain.model.Role;
import kr.taeu.SpringSecurityPractice.member.domain.model.SignUpType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="MEMBER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Embedded
	private Email email;
	
	@Embedded
	private Password password;

	@Column(name="SIGN_UP_TP")
	@Enumerated(EnumType.STRING)
	private SignUpType signUpType;
	
	@Column(name="ROLE", nullable=false)
	@Enumerated(EnumType.STRING)
	@NotNull
	private Role role;
	
	@Builder
	public Member(Email email, Password password, Role role, SignUpType signUpType) {
		this.email = email;
		this.password = password;
		this.role = role;
		this.signUpType = signUpType;
	}
}
