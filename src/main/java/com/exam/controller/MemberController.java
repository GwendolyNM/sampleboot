package com.exam.controller;


import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.exam.dto.Member;
import com.exam.security.UsernamePasswordAuthenticationToken;
import com.exam.service.MemberService;


@Controller
public class MemberController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	MemberService memberService;
	
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@GetMapping(value={"/signup"})
	public String showSignupPage(ModelMap model) {
		
		model.put("member", new Member());  // modelAttribute="member", 반드시 Command bean 이름으로 key값을 설정해야 됨.
		return "memberForm";
	}

	@PostMapping(value={"/signup"})
	public String showSignUpSuccessPage(@Valid Member member, BindingResult result) {
		logger.debug("logger:{}", member);
		
		if(result.hasErrors()) {
			return "memberForm";
		}
		//memberService와 연동
		
		//비번 암호화 작업 security에서 추가된 작업임
		String encptPw = 
				new BCryptPasswordEncoder().encode(member.getPasswd());
		//암호화된 비번을 member에 전달
		member.setPasswd(encptPw);
		
		//계정save
		int n = memberService.save(member);
		
		return "redirect:home";
	}
	
	@GetMapping(value={"/mypage"})
	public String mypage() {
		
		// Authentication의 실제 데이터는
		
		//UsernamePasswordAuthenticationToken token
		//= new UsernamePasswordAuthenticationToken(mem, null, list);
		//객체 생성때 3가지 파라미터 중 mem정보를 얻어야한다 mem 정보는 principle에 저장되어있기 때문에
		
		// AuthProvider에 저장된 Authentication 얻기
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Member xxx = (Member)auth.getPrincipal();//principle 호출함
		return "memberForm";
	}
	
	
}
