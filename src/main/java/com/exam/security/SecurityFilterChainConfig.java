package com.exam.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityFilterChainConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		//커스터마이징 처리
		
		//1. 불필요한 인증제거
		http.authorizeHttpRequests()
			.antMatchers("/login","/home","/signup","/webjars/**","/images/**").permitAll()//인증 skip
			.anyRequest()
			.authenticated();//나머지 페이지는 인증 필요
		
		//2. 로그인 관련 작업 default 로그인화면 말고 내가 만든 화면
		http.csrf().disable();
		http.formLogin() //사용자가 만든 로그인화면으로 인증처리
			.loginPage("/login") // 로그인 페이지로 갈 수 있는 요청맵핑값 <a href="login">
			.loginProcessingUrl("/auth") //  <form action="auth"
			.usernameParameter("userid") //username이 사람이름이 아니고 id이다 <input name = "userid">
			.passwordParameter("passwd") //<input name = "passwd">
			.failureForwardUrl("/login_fail") //로그인 실패시 리다이렉트되는 요청맵핑값
			.successForwardUrl("/login_success"); //로그인 성공시 리다이렉트되는 요청맵핑값
		
		//3. csrf 비활성화
		
		//4. 로그아웃 관련 작업
		http.logout()
			.logoutUrl("/logout") // security가 자동으로 로그아웃 처리해주는 요청맵핑값
			.logoutSuccessUrl("/home"); //로그아웃 성공시 리다이렉트 되는 요청맵핑값
			
			
			
			return http.build();//HttpSecurity와 상속관계, 예외처리 throw Exception한다
	}
	
}
