package kr.taeu.SpringSecurityPractice.member.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.taeu.SpringSecurityPractice.member.service.MemberDetailsService;
import lombok.RequiredArgsConstructor;

@RequestMapping("/member/resource")
@Controller
@RequiredArgsConstructor
public class MemberDetailsController {
	private final MemberDetailsService memberDetailsService;

	
	@GetMapping(value = "/signsuccess")
	@ResponseBody
	public String signSuccess(Authentication authentication) {
		return authentication.getPrincipal().toString();
	}

	
//	@GetMapping(value = "/signsuccess")
//	public String signSuccess(Model model, Authentication authentication) {
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
//		model.addAttribute("name", authentication.getName());
//		return "signsuccess";
//	}
}
