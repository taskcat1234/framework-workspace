package com.kh.spring.member.model.service;


import javax.servlet.http.HttpSession;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.kh.spring.exception.AuthenticationException;
import com.kh.spring.exception.PasswordNotMatchException;
import com.kh.spring.member.model.dao.MemberMapper;
import com.kh.spring.member.model.dto.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServicelmpl implements MemberService{
	
	
	private final MemberMapper memberMapper;
	
	private final SqlSessionTemplate sqlSession;
	private final passwordEncoder passwordEncoder;
	private final MemberValidator validator;
	
	/*
	@Autowired
	public MemberServiceImpl(MemberDAO memberDao, SqlSessionTemplate sqlSession) {
		this.memberDao = memberDao;
		this.sqlSession = sqlSession;
	}
	*/
	
	
	
	
	@Override
	public MemberDTO login(MemberDTO member) {
		//log.info
		
		// 로그인이라는 요청을 처리해주어야하는데
		// 아이디가 10자가 넘으면 안되겠네?
		// 아이디/비밀번호 : 빈 문자열 또는 null이면 안되겠네?
		
		
		// 1. Table에 아이디가 존재해야겠다
		// 2. 비밀번호가 일치해야겠네
		// 3. 둘다 통과면 정상적으로 조회할 수 있도록 해주어야겠다
		/*
		 * SqlSession sqlSession = getSqlSession();
		 * MemberDTO loginMember =new MemberDAO().login(sqlSession, nenber);
		 * sqlSession.close();
		 * return loginMember;
		 */
		// memberDao멤버를 참조해서
		//memberMapper.login(member);
		
		// 아이디만 일치하더라도 행의 정보를 필드에 담아옴
		
		
		// 1. loginMember가 null 값과 동일하다면 아이디가 존재하지 않는다.
		/*
		if(loginMember == null) {
			throw new MemberNotFoundException("존재하지 않는 아이디입니다.");
		}
		*/
		// 2.아이디만 가지고 조회를 하기 때문에
		// 비밀번호를 검증 후
		// 비밀번호가 유효하다면 회원의 정보를 session에 담기
		// 비밀번호가 유요하지않다면 비밀번호 이상하다 출력
		validator.validatedLoginMember(member);
		MemberDTO loginMember = validator.validateMemberExists(member);
		if (passwordEncoder.matches(member.getMemberPw() , loginMember.getMemberPw())) {
			return loginMember;
		} else {
			throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
		}
	}

	@Override
	public void signUP(MemberDTO member) {
		
		/*
		if(member == null || 
		   member.getMemberId() == null || 
		   member.getMemberId().trim().isEmpty() ||
		   member.getMemberPw() == null ||
		   member.getMemberPw().trim().isEmpty()) {
		   return;
		}
		*/
		
		validator.validatedLoginMember(member);
		
		/*
		int result = memberDao.checkId(sqlSession, member.getMemberId());
		if(result > 0 ) { return;}
		*/
		
		
		validator.validatedJoinMember(member);
		//member.setMember(passwordEncoder.encode(member.getMemberPw()));
		member.setMemberPw(passwordEncoder.encode(member.getMemberPw()));
		int consequence = memberMapper.signUP(member);
		
		/*
		if(consequence > 0) {
			return;
			
		} else {
			return;
		}
		*/
		/*
		log.info("사용자가 입력한 비밀번호 평문 : {}", member.getMemberPw());
		// 암호화 하는법 .encode()호출
		log.info("평문을 암호문으로 바꾼것 : {}", PasswordEncoder.encode(member.getMemberPw()));
		*/
		// INSERT 해야지~
		//memberDao.signup
	}

	@Override
	public void update(MemberDTO member, HttpSession session) {
		MemberDTO sessionMember = (MemberDTO)session.getAttribute("loginMember");
		// 사용자 검증
		if(!member.getMemberId().equals(sessionMember.getMemberId())) {
			throw new AuthenticationException("권한없는 접근입니다.");
		}
		
		// 존재하는 아이디인지 검증
		validator.validateMemberExists(member);
		int result = memberMapper.update(member);
		// SQL문 수행 결과 검증
		
		if(result != 1) {
			throw new AuthenticationException("뭔진 모르겠는데 문제가 일어남 다시 시도해주세요");	
		}
		sessionMember.setMemberName(member.getMemberName());
		sessionMember.setEmail(member.getEmail());
		

	}
	@Override
	public int delete(MemberDTO member) {
		return 0;
	}
	
	/*
	@Override
	public int userDelete(MemberDTO member, HttpSession session) {
		MemberDTO sessionMember = (MemberDTO)session.getAttribute("loginMember");
		// 사용자 검증
		if(!member.getMemberId().equals(sessionMember.getMemberId())) {
			throw new AuthenticationException("권한없는 접근입니다.");
		}
		validator.validatedLoginMember(member);
		MemberDTO DeleteMember = validator.validateMemberExists(member);
		if (DeleteMember.matches(member.getMemberPw() , DeleteMember.getMemberPw())) {
			return DeleteMember;
		} else {
			throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
		}

	}
	*/

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
