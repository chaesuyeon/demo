package com.example.demo.controller;

import java.util.Optional;
import com.example.demo.model.domain.Board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.demo.model.service.AddArticleRequest;
import com.example.demo.model.service.BlogService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.*;



@Controller
public class MemberController {

    @GetMapping("/join_new") // 회원 가입 페이지 연결
    public String join_new() {
        return "join_new"; // .HTML 연결
    }
    @PostMapping("/api/members") // 회원 가입 저장
    public String addmembers(HttpServletRequest request) {
        // memberService is not available in this project (import unresolved); handle request parameters here or wire a valid service.
        // Example: String username = request.getParameter("username");
return "join_end"; // .HTML 연결
}

}
