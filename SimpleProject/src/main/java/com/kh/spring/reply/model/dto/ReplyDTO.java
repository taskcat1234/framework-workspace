package com.kh.spring.reply.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class ReplyDTO {
	
	private int repltNo;
	private String replyWriter;
	private String replyContent;
	private Date createDate;
	private int refBoardNo;
}
