package kr.taeu.SpringSecurityPractice.member.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.taeu.SpringSecurityPractice.member.dto.MemberResponse;
import kr.taeu.SpringSecurityPractice.member.dto.SignUpRequest;
import kr.taeu.SpringSecurityPractice.member.service.MemberDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(value = "/member")
@Controller
@RequiredArgsConstructor
public class MemberController {
	private final MemberDetailsService memberDetailsService;
	
	@GetMapping(value = "/signup")
	public String signUp(Model model) {
		return "signup";
	}
	
	@PostMapping(value = "/signup")
	@ResponseBody
	public MemberResponse signUp(@RequestBody @Valid final SignUpRequest signUpRequest) {
		return new MemberResponse(memberDetailsService.signUp(signUpRequest));
	}
	
	@GetMapping(value = "/signin")
	public String signIn(Model model) {
		return "signin";
	}
}