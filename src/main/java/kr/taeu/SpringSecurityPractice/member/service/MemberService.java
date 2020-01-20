package kr.taeu.SpringSecurityPractice.member.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kr.taeu.SpringSecurityPractice.member.dao.MemberRepository;
import kr.taeu.SpringSecurityPractice.member.domain.Member;
import kr.taeu.SpringSecurityPractice.member.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;

	@Transactional
	public Member signUp(SignUpRequest req) {
		boolean isExists = this.memberRepository.existsByEmail(req.getEmail());
		if(isExists) {
			//이메일 중복 처리
			throw new RuntimeException("이메일 중복");
		}
		return this.memberRepository.save(req.toEntity());
	}
}
