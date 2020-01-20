package kr.taeu.SpringSecurityPractice.global.security.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import kr.taeu.SpringSecurityPractice.member.dao.MemberRepository;
import kr.taeu.SpringSecurityPractice.member.domain.Member;
import kr.taeu.SpringSecurityPractice.member.domain.model.Email;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{
	private final MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Member> findMember = memberRepository.findByEmail(new Email(email));
		findMember.orElseThrow(() -> new UsernameNotFoundException(email));
		Member member = findMember.get();
		
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		
		grantedAuthorities.add(new SimpleGrantedAuthority(member.getRole().getValue()));
		
		return new User(member.getEmail().getValue(), member.getPassword().getValue(), grantedAuthorities);
	}

}
