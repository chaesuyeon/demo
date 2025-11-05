package com.example.demo.controller;

import java.util.List;
import java.util.Optional;
import com.example.demo.model.domain.Article;
import com.example.demo.model.domain.Board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.demo.model.service.AddArticleRequest;
import com.example.demo.model.service.BlogService;
import org.springframework.web.bind.annotation.*;



@Controller
public class BlogController {

    @Autowired
    BlogService testService;

    // @GetMapping("/article_list")
    // public String article_list(Model model) {
    //     List<Article> list = testService.findAll();
    //     model.addAttribute("articles", list);
    //     return "article_list";
    // }


    @GetMapping("/board_list") // 새로운 게시판 링크 지정
    public String board_list(Model model) {
        List<Board> list = testService.findAll(); // 게시판 전체 리스트, 기존 Article에서 Board로 변경됨
        model.addAttribute("boards", list); // 모델에 추가
        return "board_list"; // .HTML 연결
    }


//    @GetMapping("/article_edit/{id}")
// public String article_edit(Model model, @PathVariable Long id) {
//     Optional<Article> list = testService.findById(id);
//     if (list.isPresent()) {
//         model.addAttribute("article", list.get());
//     } else {
//         return "article_error"; 
//     }
//     return "article_edit";
// }


    @PutMapping("/api/board_edit/{id}")
    public String updateBoard(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
        testService.update(id, request);
        return "redirect:/board_list";
    }

    @DeleteMapping("/api/board_delete/{id}")
    public String deleteBoard(@PathVariable Long id) {
        testService.delete(id);
        return "redirect:/board_list";
    }

    @GetMapping("/board_view/{id}") // 게시판 링크 지정
public String board_view(Model model, @PathVariable Long id) {
    Optional<Board> list = testService.findById(id); // 선택한 게시판 글
    if (list.isPresent()) {
        model.addAttribute("boards", list.get()); // 존재할 경우 실제 Board 객체를 모델에 추가
    } else {
        // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
        return "/error_page/article_error"; // 오류 처리 페이지로 연결
    }
    return "board_view"; // .HTML 연결
}

}
