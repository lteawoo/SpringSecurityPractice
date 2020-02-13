package kr.taeu.SpringSecurityPractice.member.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.taeu.SpringSecurityPractice.member.dao.MemberRepository;
import kr.taeu.SpringSecurityPractice.member.domain.Member;
import kr.taeu.SpringSecurityPractice.member.domain.model.Email;
import kr.taeu.SpringSecurityPractice.member.domain.model.Password;
import kr.taeu.SpringSecurityPractice.member.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public Member signUp(SignUpRequest req) {
		boolean isExists = memberRepository.existsByEmail(req.getEmail());
		
		if(isExists) {
			//이메일 중복 처리
			throw new RuntimeException("이메일 중복");
		}
		
		//비밀번호 암호화
		SignUpRequest cryptedReq = new SignUpRequest(req.getEmail(),
				new Password(passwordEncoder.encode(req.getPassword().getValue())),
				req.getRole());
		
		return this.memberRepository.save(cryptedReq.toEntity());
	}
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Member> findMember = memberRepository.findByEmail(new Email(email));
		findMember.orElseThrow(() -> new UsernameNotFoundException("Incorrect Login credentials"));
		Member member = findMember.get();
		
		/*
		 * 권한 설정 > 예제이므로 멤버당 한개씩이라고 가정함
		 */
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority(member.getRole().getValue()));
		
		return new User(member.getEmail().getValue(), member.getPassword().getValue(), grantedAuthorities);
	}
}
