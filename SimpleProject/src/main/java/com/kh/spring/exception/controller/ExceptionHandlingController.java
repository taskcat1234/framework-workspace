package com.kh.spring.exception.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.exception.InvalidParameterException;
import com.kh.spring.exception.MemberNotFoundException;
import com.kh.spring.exception.PasswordNotMatchException;
import com.kh.spring.exception.TooLargeValueException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionHandlingController {
	
	private ModelAndView createErrorResponse(String errorMsg, Exception e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", e.getMessage())
		  .setViewName("include/error_page");
		log.info("발생예외 : {}", e);
		return mv;
	}
	
	@ExceptionHandler(TooLargeValueException.class)
	protected ModelAndView runTimeError(TooLargeValueException e) {
		return createErrorResponse(e.getMessage(), e);
	}
	
	@ExceptionHandler(InvalidParameterException.class)
	protected ModelAndView runTimeError(InvalidParameterException e) {
		return createErrorResponse(e.getMessage(), e);
	}
	
	@ExceptionHandler(MemberNotFoundException.class)
	protected ModelAndView runTimeError(MemberNotFoundException e) {
		return createErrorResponse(e.getMessage(), e);
	}
	
	@ExceptionHandler(PasswordNotMatchException.class)
	protected ModelAndView runTimeError(PasswordNotMatchException e) {
		return createErrorResponse(e.getMessage(), e);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	@ExceptionHandler(TooLargeValueException.class)
	protected ModelAndView runTimeError(TooLargeValueException e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", e.getMessage())
		  .setViewName("include/error_page");
		return mv;
	}
	
	@ExceptionHandler(InvalidParameterException.class)
	protected ModelAndView runTimeError(InvalidParameterException e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", e.getMessage())
		  .setViewName("include/error_page");
		return mv;
	}
	*/
}
