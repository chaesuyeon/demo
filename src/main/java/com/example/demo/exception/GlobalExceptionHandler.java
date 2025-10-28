package com.example.demo.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // URL 파라미터 타입 불일치 예외 (예: /article_edit/abcd)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch(MethodArgumentTypeMismatchException ex, Model model) {
        model.addAttribute("errorTitle", "잘못된 게시판 접근입니다!");
        model.addAttribute("errorMessage", "요청하신 게시글을 찾을 수 없거나 접근 권한이 없습니다.");
        return "article_error"; 
    }
}
