package kr.taeu.SpringSecurityPractice.global.security.oauth2.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@Entity
//@Table(name="OAUTH_APPROVALS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthApprovals {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String userId;
	
	@Column
	private String clientId;
	
	@Column
	private String scope;
	
	@Column
	private String status;
	
	@Column
	private LocalDateTime expireAt;
	
	@Column
	private LocalDateTime lastModifiedAt;
	
	@Builder
	public OAuthApprovals(String userId, String clientId, String scope, LocalDateTime expireAt, LocalDateTime lastModifiedAt) {
		this.userId = userId;
		this.clientId = clientId;
		this.scope = scope;
		this.expireAt = expireAt;
		this.lastModifiedAt = lastModifiedAt;
	}
}
