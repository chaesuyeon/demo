package com.example.demo.controller;

import com.example.demo.model.domain.Member;
import com.example.demo.model.service.AddMemberRequest;
import com.example.demo.model.service.MemberService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    // 회원가입 페이지 이동
    @GetMapping("/join_new")
    public String join_new() {
        return "join_new";
            }
    // 회원가입 처리
    @PostMapping("/api/members")
    public String addmembers(@ModelAttribute AddMemberRequest request) {
        memberService.saveMember(request);
        return "join_end"; // 가입 완료 페이지
    }

    // 로그인 페이지 이동
    @GetMapping("/member_login")
    public String member_login() {
        return "login";
    }

    // 로그인 체크
    // 로그인 체크
    @PostMapping("/api/login_check")
    public String checkMembers(
            @ModelAttribute AddMemberRequest request,
            Model model,
            HttpServletRequest req,
            HttpServletResponse res
    ) {
        try {
            // 1. 기존 세션 제거
            HttpSession session = req.getSession(false);
            if (session != null) {
                session.invalidate(); // 기존 세션 무효화

                Cookie cookie = new Cookie("JSESSIONID", null);
                cookie.setPath("/");
                cookie.setMaxAge(0); // 쿠키 삭제
                res.addCookie(cookie);
            }

            // 2. 새로운 세션 생성
            session = req.getSession(true);

            // 3. 로그인 검증
            Member member = memberService.loginCheck(request.getEmail(), request.getPassword());

            // 4. 로그인 성공 → 세션 저장
            session.setAttribute("userId", member.getId());  // 또는 member.getEmail()

            // 5. 게시판 페이지로 이동
            return "redirect:/board_list";

        } catch (IllegalArgumentException e) {
            // 로그인 실패 → 에러 메시지 전달
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }
@GetMapping("/api/logout")
public String member_logout(Model model, HttpServletRequest request, HttpServletResponse response) {

    // 1. 기존 세션 가져오기 (없을 수도 있음)
    HttpSession session = request.getSession(false);

    if (session != null) {
        // 2. 기존 세션 무효화
        session.invalidate();

        // 3. JSESSIONID 쿠키 삭제
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setMaxAge(0); // 즉시 삭제
        response.addCookie(cookie);
    }

    // 4. 로그 확인
    System.out.println("로그아웃 완료. 기존 세션 삭제됨.");

    // 5. 로그인 페이지로 이동
    return "login";
}

}

