package kr.taeu.SpringSecurityPractice.member.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.taeu.SpringSecurityPractice.member.dto.MemberResponse;
import kr.taeu.SpringSecurityPractice.member.dto.SignInRequest;
import kr.taeu.SpringSecurityPractice.member.dto.SignUpRequest;
import kr.taeu.SpringSecurityPractice.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	
	@GetMapping(value = "/api/status")
	public String isRunning() {
		return "is Running...";
	}
	
	@GetMapping(value = "/api/signin")
	public String signIn(@RequestBody @Valid final SignInRequest signInRequest) {
		return "login?";
	}
	
	@PostMapping(value = "/api/signup")
	public MemberResponse signUp(@RequestBody @Valid final SignUpRequest signUpRequest) {
		return new MemberResponse(memberService.signUp(signUpRequest));
	}
	
//	@PostMapping(value ="/api/signIn")
//	public MemberResponse signIn(@Valid SignInRequest signInRequest) {
//		
//	}
}
