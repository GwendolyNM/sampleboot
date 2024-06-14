package com.exam.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.exam.dto.Member;
import com.exam.service.MemberService;

@Component
public class AuthProvider implements AuthenticationProvider {
	@Autowired
	MemberService memberService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		//입력한 id pw를 저장함
		String userid = (String)authentication.getPrincipal(); // name="userid"
		String passwd = (String)authentication.getCredentials(); // name="passwd"
		//여기 저장된 id pw 값으로 db에 저장된 id pw를 비교할 수 있다
//		.failureForwardUrl("/login_fail") //로그인 실패시 리다이렉트되는 요청맵핑값
//		.successForwardUrl("/login_success"); //로그인 성공시 리다이렉트되는 요청맵핑값
		//비교시 맞으면 login_success 틀리면 login_fail으로 처리
		
		Member mem = memberService.findById(userid);
		String encrptPw = mem.getPasswd();
		
		//Authentication 하위클래스
		//로그인 성공시
		UsernamePasswordAuthenticationToken token=null;
		
		if(mem!=null && new BCryptPasswordEncoder().matches(passwd, encrptPw)) {
			
			List<GrantedAuthority> list = new ArrayList<>();
			list.add(new SimpleGrantedAuthority("USER")); //사용자면 ADMIN
			
			//암호화된 비밀번호(mem)를 저장하면 보인 이슈가 있다
			mem.setPasswd(passwd);
			token = new UsernamePasswordAuthenticationToken(mem, null, list);
			return token;
		}//passwd는 1234 encrptPw는 암호화된 passwd이기 때문에 BCryptPasswordEncoder를 이용해서 비교해야한다
		
	
	//로그인 실패시
	throw new badCredentialsException("비밀번호가 일치하지 않습니다");
	
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		
		
		return true;
	}

}
