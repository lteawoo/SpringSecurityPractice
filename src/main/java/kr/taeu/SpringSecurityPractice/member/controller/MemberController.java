package kr.taeu.SpringSecurityPractice.member.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(value = "/member")
@Controller
@RequiredArgsConstructor
public class MemberController {
	private final OAuth2AuthorizedClientService authorizedClientService;
	private final MemberService memberService;
	
	@GetMapping(value = "/")
	@ResponseBody
	public String helloWorld() {
		return "hello";
	}
	
	@GetMapping(value = "/status")
	@ResponseBody
	public String isRunning(HttpServletRequest request) {
//		OAuth2AuthorizedClient client = authorizedClientService
//				.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(),
//						authentication.getName());
		Enumeration<String> s = request.getHeaderNames();
		while(s.hasMoreElements()) {
			log.info(request.getHeader(s.nextElement()).intern());
		} //JSESSION을 남긴다... -> 굳이 필요한가? FRONT로넘겨보자.
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