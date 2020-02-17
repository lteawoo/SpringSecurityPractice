package kr.taeu.SpringSecurityPractice.global.security.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.SpringSecurityCoreVersion;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
	
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
	private final Object principal;
	private Object credentials;

	public JwtAuthenticationToken(Object credentials) {
		super(null);
		this.principal = null;
		this.credentials = credentials;
		super.setAuthenticated(false);
	}
	
	@Override
	public Object getPrincipal() {
		return this.principal;
	}

	@Override
	public Object getCredentials() {
		return this.credentials;
	}
	@Override
	public void setAuthenticated(boolean authenticated) {
		if (isAuthenticated()) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
       }
	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
		this.credentials = null;
	}
}
