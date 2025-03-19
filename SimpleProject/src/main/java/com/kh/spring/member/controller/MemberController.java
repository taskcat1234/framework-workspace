package com.kh.spring.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;


import com.kh.spring.member.model.dto.MemberDTO;
import com.kh.spring.member.model.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor // 의존성주입 생성자를 생성해주는 애노테이션
public class MemberController {
	
	//@Autowired 1번
	private final MemberService memberService;
	
	/*
	@Autowired 2번
	public void setMemberService(MemberService memberService) {
		this.memberServuce = memberService;
	}
	*/
	
	/*
	@Autowired // 3번
	public MemberController(MemberService memberService) {
		this.memberServuce = memberService;
	}
	*/
	
	/* 1번 방법의 경우 밑에와 같음
	@RequestMapping(value="login")
	public String login(HttpServletRequest request) {
		//System.out.println("나는 로그인 요청오면 출동함");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		//System.out.println("id : " + id + ", pw : " + pw);
		//System.out.printf("id : %s, pw : %s", id, pw);
		log.info("id : {}, pw : {}", id, pw);
		return "main_page";
	}
	*/
	
	/*
	// 2번 방법의 경우 밑에와 같음
	@RequestMapping("login")
	public String login(@RequestParam(value="id", defaultValue="abcde") String id,
						@RequestParam(value="pw") String pw){
		
		log.info("이렇게도 넘어오나요? id : {} pw : {}", id, pw);
		
		return "main_page";
	}
	*/
	
	/*
	// 3번 방법의 경우 밑에와 같음
	@PostMapping("login")
	public String login(String id, String pw) {
		
		log.info("이렇게도 넘어오나요? id : {} pw : {}", id, pw);
		
		MemberDTO member = new MemberDTO();
		member.setMemberId(id);
		member.setMemberPw(pw);
		
		
		return "main_page";
	}
	*/
	// private MemberService memberServuce;
	/**
	 * 커맨드 객체 방식
	 * 1. 매개변수 자료형에 반드시 기본생성자가 존재할 것
	 * 2. 전달되는 키값과 객체의 필드명이 동일할 것 header 의 name 값
	 * 3. setter메서드가 반드시 존재할 것
	 * 
	 * 스프링에서 해당 객체를 기본생성자를 통해서 생성한 후 내부적으로 setter메서드를 찾아서 요청 시 전달값을 해당 필드에 대입해줌
	 * (Setter Injection)
	 */
	@PostMapping("login")
	public String login(MemberDTO member, HttpSession session, Model model) {
		log.info("로그 테스트 {}", member);
		
		/* 데이터가공    => 패스
		 * 요청처리     =>  
		 * 응답화면지정  =>
		 */
		MemberDTO loginMember = memberService.login(member);
		
		/*
		if(loginMember != null) {
			log.info("로그인 성공");
		} else {
			log.info("로그인 실패");
		}
		*/
		
		if(loginMember != null) { // 성공했을때
			// sessionScope에 로그인 정보를 담아줌
			session.setAttribute("loginMember", loginMember);
			// 그 다음에
			// main_page
			// /WEB-INF/views
			// .jsp
			// => 포워딩
			// sendRedirect
			return "redirect:/";
			
		} else { // 실패했을떄
			// error_page
			// requestScope에 에러구문을 담아서 
			// Spring에서는 Model객체를 이용해서 RequestScope에 값을 담음
			model.addAttribute("message", "로그인 실패!");
			
			// forwarding
			// /WEB-INF/view/
			// include/error_page
			// .jsp
			return "include/error_page";
		}
		
		//return "main_page";
	}
	
	public void join(MemberDTO member) {
		
	}
	
	
	
	
	
	
	
	
	
	
}
