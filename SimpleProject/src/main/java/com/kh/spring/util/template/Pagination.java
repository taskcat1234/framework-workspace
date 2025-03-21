package com.kh.spring.util.template;

import com.kh.spring.util.model.dto.Pagelnfo;

public class Pagination {
	public static Pagelnfo getPageInfo(
										int count,
										int currentPage,
										int boardLimit,
										int pageLimit) {
		int maxPage = (int)Math.ceil((double)count/boardLimit);
		int startPage = (currentPage - 1) / pageLimit * pageLimit +1;
		int endPage = startPage + pageLimit - 1;
		
		if(endPage > maxPage) {endPage = maxPage;}
		
		/*
		new PageInfo(count,currentPage,boardLimit, pageLimit, maxPage, startPage, endPage);
		
		PageInfo page = new PageInfo();
		page.setBoardLimit(boardLimit);
		page.setCount(count);
		*/
		
		
		return Pagelnfo.builder().boardLimit(boardLimit).count(count).currentPage(currentPage).
				startpage(startPage).endpage(endPage).maxPage(maxPage).pageLimit(pageLimit).build();
	}
}
