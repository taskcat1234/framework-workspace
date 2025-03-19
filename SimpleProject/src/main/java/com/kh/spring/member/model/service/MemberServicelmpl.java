package com.kh.spring.member.model.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberDAO;
import com.kh.spring.member.model.dto.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServicelmpl implements MemberService{
	
	
	private final MemberDAO memberDao;
	private final SqlSessionTemplate sqlSession;
	
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
		if(member.getMemberId().length() > 10) {
			return null;
		}
		if(member == null || 
		   member.getMemberId() == null || 
		   member.getMemberId().trim().isEmpty() ||
		   member.getMemberPw() == null ||
		   member.getMemberPw().trim().isEmpty()) {
			return null;
		}
		
		
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
		MemberDTO loginMember = memberDao.login(sqlSession, member);
		
		
		return loginMember;
	}

	@Override
	public MemberDTO signUP(MemberDTO member) {
		return null;
	}

	@Override
	public MemberDTO update(MemberDTO member) {
		return null;
	}

	@Override
	public int delete(MemberDTO member) {
		return 0;
	}

}
