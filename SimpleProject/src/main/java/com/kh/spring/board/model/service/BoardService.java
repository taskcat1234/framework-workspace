package com.kh.spring.board.model.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.BoardDTO;

public interface BoardService {
	
	// 게시글 작성(파일첨부)
	void insertBoard(BoardDTO board, MultipartFile file, HttpSession session);
	/* 보편적으로 많이 쓰는 단어
	 * insertBoard();
	 * seve();
	 */
	
	// 게시글 목록조회
	Map<String, Object> selectBoardList(int currentPage);
	/*
	 * selectBoardList();
	 * selectAll();
	 * findAll();
	 */
	
	// 게시글 상세보기(댓글도 같이 조회) --> 새로운 기술 사용
	BoardDTO selectBoard(int boardNo);
	/*
	 * selectBoard();
	 * findbyXXXX();
	 */

	// 게시글 수정
	BoardDTO updateBoard(BoardDTO board, MultipartFile file);
	/*
	 *	updateBoard();
	 *	updateByXXX(); 
	 */

	// 게시글 삭제 (딜리트인척하고 업데이트 할것 STATUS컬럼값 N으로 바꿀것)
	void deleteBoard(int boardNo);

	// --------- 1절
	
	// 게시글 검색 기능
	Map<String, Object> doSearch(Map<String, String> map);
	
	// 댓글 작성
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
