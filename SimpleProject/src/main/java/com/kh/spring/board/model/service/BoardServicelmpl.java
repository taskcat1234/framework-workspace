package com.kh.spring.board.model.service;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.mapper.BoardMapper;
import com.kh.spring.exception.AuthenticationException;
import com.kh.spring.member.model.dto.MemberDTO;
import com.kh.spring.reply.model.dto.ReplyDTO;
import com.kh.spring.util.model.dto.Pagelnfo;
import com.kh.spring.util.template.Pagination;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServicelmpl implements BoardService {
	
	private final BoardMapper boardMapper;
	
	private void validateUser(HttpSession session, BoardDTO board) {
		MemberDTO loginMember = (MemberDTO)session.getAttribute("loginMember");
		if(loginMember != null && !loginMember.getMemberId().equals(board.getBoardWriter())) {
			throw new AuthenticationException("권한 없는 접근입니다.");
		}
	}
	
	private void validateContent(BoardDTO board) {
		if(
			board.getBoardTitle() == null || board.getBoardTitle().trim().isEmpty() ||
			board.getBoardContent() == null || board.getBoardContent().trim().isEmpty() ||
			board.getBoardWriter() == null || board.getBoardWriter().trim().isEmpty()) {
				throw new InvalidParameterException("유효하지 않은 요청입니다.");
			}
	}
	
	//private void
	
	@Override
	public void insertBoard(BoardDTO board, MultipartFile file, HttpSession session) {
		//1. 권한 체크
		validateUser(session, board);
		
		//2. 유효성 검사
		validateContent(board);
		//2-2
		
		/*
		 * <,>,\, &
		 */
		
		String boardTitle = board.getBoardTitle()
												 .replaceAll("<", "&lt;")
												 .replaceAll(">", "&gt;")
												 .replaceAll("\n", "<br>");
		String boardContent = board.getBoardContent()
												   .replaceAll("<", "&lt;")
				 								   .replaceAll(">", "&gt;")
				 								   .replaceAll("\n", "<br>");
		board.setBoardTitle(boardTitle);
		board.setBoardContent(boardContent);
		
		
		// 3) 파일유무 체크// 이름 바꾸기 + 저장
		if(!file.getOriginalFilename().isEmpty()) {
			// 이름 바꾸기
			
			// KH_현재시간+랜덤숫자+원본파일확장자
			
			StringBuilder sb = new StringBuilder();
			sb.append("KH_");
			//log.info("현재시간 : {}", new Date()); // 현재시간 : Fri Mar 21 10:15:20 KST 2025
			String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			sb.append(currentTime);
			sb.append("_");
			int random = (int)(Math.random() * 900) + 100;
			sb.append(random);
			String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			sb.append(ext);
			//log.info("바뀐 파일명 : {}", sb.toString());
			ServletContext application = session.getServletContext();
		
			String savePath = application.getRealPath("/resources/upload_files/");
			
			try {
				file.transferTo(new File(savePath+ sb.toString()));
			} catch (IllegalStateException | IOException e ) {
				e.printStackTrace();
			}
			board.setChangeName("/spring/resources/upload_files/" + sb.toString());
		}
		
		boardMapper.insertBoard(board);
	}

	@Override
	public Map<String, Object> selectBoardList(int currentPage) {
		
		List<BoardDTO> boards = new ArrayList();
		Map<String, Object> map = new HashMap();
		
		int count = boardMapper.selectTotalCount();
		Pagelnfo pi = Pagination.getPageInfo(count,currentPage,5,5);
		
		if(count != 0) {
			RowBounds rb = new RowBounds((currentPage - 1) *5, 5);
			boards = boardMapper.selectBoardList(rb);
		}

		map.put("boards", boards);
		map.put("pagelnfo", pi);
		
		
		return map;
	}

	@Override
	public BoardDTO selectBoard(int boardNo) {
		
		// 1절
		//BoardDTO board = boardMapper.selectBoard(boardNo);
		
		// 2절
		//List<ReplyDTO> replyList = boardMapper.selectReplyList(boardNo);
		//board.setReplyList(replyList);
		
		// 3절
		BoardDTO board = boardMapper.selectBoardAndReply(boardNo);
		if(board == null) {
			throw new InvalidParameterException("존재하지않는 게시글입니다.");
		}
		return board;
	}

	@Override
	public BoardDTO updateBoard(BoardDTO board, MultipartFile file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteBoard(int boardNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Object> doSearch(Map<String, String> map) {
		//했다고 침 map에서 get("condition") / get("ketword") 값이 비었나 안비었나 확인
		
		int searchedCount = boardMapper.searchedCount(map);
		
		log.info("넘어온 값? : {}", searchedCount);
		 
		Pagelnfo pi = Pagination.getPageInfo(searchedCount,
											  Integer.parseInt(map.get("currentPage")),
											  3,
											  3);
		
		RowBounds rb = new RowBounds((pi.getCurrentPage() - 1) * 3, 3 );
		
		List<BoardDTO> boards = boardMapper.selectSearchList(map,rb);
		
		Map<String,Object> returnValue = new HashMap();
		returnValue.put("boards", boards);
		returnValue.put("pageInfo", pi);
		
		
		return returnValue;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
