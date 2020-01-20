package kr.taeu.SpringSecurityPractice.member.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.taeu.SpringSecurityPractice.member.domain.Member;
import kr.taeu.SpringSecurityPractice.member.domain.model.Email;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByEmail(Email email);
	
	boolean existsByEmail(Email email);
}
