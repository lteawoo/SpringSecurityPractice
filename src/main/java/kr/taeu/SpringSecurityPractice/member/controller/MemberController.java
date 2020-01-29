package kr.taeu.SpringSecurityPractice.member.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.taeu.SpringSecurityPractice.member.dto.MemberResponse;
import kr.taeu.SpringSecurityPractice.member.dto.SignUpRequest;
import kr.taeu.SpringSecurityPractice.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@RequestMapping(value = "/member")
@RestController
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	
	@GetMapping(value = "/")
	public String helloWorld() {
		return "hello";
	}
	
	@GetMapping(value = "/status")
	public String isRunning() {
		return "is Running...";
	}
	
	@PostMapping(value = "/signup")
	public MemberResponse signUp(@RequestBody @Valid final SignUpRequest signUpRequest) {
		return new MemberResponse(memberService.signUp(signUpRequest));
	}
}