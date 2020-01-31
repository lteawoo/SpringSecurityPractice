package kr.taeu.SpringSecurityPractice.member.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.taeu.SpringSecurityPractice.member.dto.MemberResponse;
import kr.taeu.SpringSecurityPractice.member.dto.SignUpRequest;
import kr.taeu.SpringSecurityPractice.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@RequestMapping(value = "/member")
@Controller
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	
	@GetMapping(value = "/")
	@ResponseBody
	public String helloWorld() {
		return "hello";
	}
	
	@GetMapping(value = "/status")
	@ResponseBody
	public String isRunning() {
		return "is Running...";
	}
	
	@GetMapping(value = "/signup")
	public String signUp(Model model) {
		return "signup";
	}
	
	@PostMapping(value = "/signup")
	@ResponseBody
	public MemberResponse signUp(@RequestBody @Valid final SignUpRequest signUpRequest) {
		return new MemberResponse(memberService.signUp(signUpRequest));
	}
	
	@GetMapping(value = "/signin")
	public ModelAndView signIn() {
		return new ModelAndView("signin");
	}
}