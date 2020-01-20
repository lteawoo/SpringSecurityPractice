package kr.taeu.SpringSecurityPractice.member.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import kr.taeu.SpringSecurityPractice.member.domain.model.Email;
import kr.taeu.SpringSecurityPractice.member.domain.model.Password;
import kr.taeu.SpringSecurityPractice.member.domain.model.Role;
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
	
	@Embedded
	private Role role;
	
	@Builder
	public Member(Email email, Password password, Role role) {
		this.email = email;
		this.password = password;
		this.role = role;
	}
}
