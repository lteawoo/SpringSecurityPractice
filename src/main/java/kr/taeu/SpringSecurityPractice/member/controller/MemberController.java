package kr.taeu.SpringSecurityPractice.member.controller;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
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
	
	@GetMapping(value = "/signsuccess")
	public String signSuccess(Model model, Authentication authentication) {
//		OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());
//		
//		String userInfoEndpointUri = client.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();
//		
//		if(!StringUtils.isEmpty(userInfoEndpointUri)) {
//			RestTemplate restTemplate = new RestTemplate();
//			HttpHeaders headers = new HttpHeaders();
//			
//			headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue());
//			HttpEntity entity = new HttpEntity("", headers);
//			ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
//			Map userAttributes = response.getBody();
//			
//			model.addAttribute("name", userAttributes.get("name"));
//		}
		model.addAttribute("name", authentication.getName());
		return "signsuccess";
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