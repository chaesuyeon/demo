package com.example.demo.controller;

import java.util.List;
import java.time.LocalDate;
import java.util.Optional;
import com.example.demo.model.domain.Board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.demo.model.service.AddArticleRequest;
import com.example.demo.model.service.BlogService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.*;



@Controller
public class BlogController {

    @Autowired
    BlogService blogService;

    // @GetMapping("/article_list")
    // public String article_list(Model model) {
    //     List<Article> list = blogService.findAll();
    //     model.addAttribute("articles", list);
    //     return "article_list";
    // }


    // @GetMapping("/board_list") // 새로운 게시판 링크 지정
    // public String board_list(Model model) {
    //     List<Board> list = blogService.findAll(); // 게시판 전체 리스트, 기존 Article에서 Board로 변경됨
    //     model.addAttribute("boards", list); // 모델에 추가
    //     return "board_list"; // .HTML 연결
    // }

   @GetMapping("/board_list") 
public String board_list(
        Model model,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "") String keyword,
        HttpSession session) {

    Object rawUserId = session.getAttribute("userId");
    if (rawUserId == null) {
        return "redirect:/member_login";
    }

    String email = (String) session.getAttribute("email");

    int pageSize = 20;
    PageRequest pageable = PageRequest.of(page, pageSize);

    Page<Board> list = keyword.isEmpty()
            ? blogService.findAll(pageable)
            : blogService.searchByKeyword(keyword, pageable);

    // 글 번호 계산
    int startNum = (page * pageSize) + 1;

    model.addAttribute("boards", list);
    model.addAttribute("totalPages", list.getTotalPages());
    model.addAttribute("currentPage", page);
    model.addAttribute("keyword", keyword);
    model.addAttribute("email", email);

    // 글 번호 HTML로 전달
    model.addAttribute("startNum", startNum);

    return "board_list";
}


    
    @GetMapping("/board_write")
    public String board_write() {
    return "board_write";
    }

//    @GetMapping("/article_edit/{id}")
// public String article_edit(Model model, @PathVariable Long id) {
//     Optional<Article> list = blogService.findById(id);
//     if (list.isPresent()) {
//         model.addAttribute("article", list.get());
//     } else {
//         return "article_error"; 
//     }
//     return "article_edit";
// }


@PostMapping("/api/boards")
public String addboards(@ModelAttribute AddArticleRequest request,
                        HttpSession session) {

    String loginEmail = (String) session.getAttribute("email");
    if (loginEmail == null) {
        loginEmail = "GUEST";
    }

    request.setUser(loginEmail);

    // 진짜 날짜 넣기
    request.setNewdate(LocalDate.now().toString());

    // 숫자 값 넣기
    request.setCount("0");
    request.setLikec("0");

    blogService.save(request);
    return "redirect:/board_list";
}



    @PutMapping("/api/board_edit/{id}")
    public String updateBoard(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
        blogService.update(id, request);
        return "redirect:/board_list";
    }

    @DeleteMapping("/api/board_delete/{id}")
    public String deleteBoard(@PathVariable Long id) {
        blogService.delete(id);
        return "redirect:/board_list";
    }

    @GetMapping("/board_view/{id}") // 게시판 링크 지정
public String board_view(Model model, @PathVariable Long id) {
    Optional<Board> list = blogService.findById(id); // 선택한 게시판 글
    if (list.isPresent()) {
        model.addAttribute("boards", list.get()); // 존재할 경우 실제 Board 객체를 모델에 추가
    } else {
        // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
        return "article_error"; // 오류 처리 페이지로 연결
    }
    return "board_view"; // .HTML 연결
}

@GetMapping("/board_edit/{id}")
public String board_edit(Model model, @PathVariable Long id) {

    Optional<Board> board = blogService.findById(id);

    if (board.isPresent()) {
        model.addAttribute("board", board.get());
        return "board_edit";
    } else {
        return "article_error"; // 없는 글이면 예외 페이지
    }
}


}
