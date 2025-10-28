package com.example.demo.controller;

import java.util.List;
import java.util.Optional;
import com.example.demo.model.domain.Article;
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

    @GetMapping("/article_list")
    public String article_list(Model model) {
        List<Article> list = testService.findAll();
        model.addAttribute("articles", list);
        return "article_list";
    }

   @GetMapping("/article_edit/{id}")
public String article_edit(Model model, @PathVariable Long id) {
    Optional<Article> list = testService.findById(id);
    if (list.isPresent()) {
        model.addAttribute("article", list.get());
    } else {
        return "article_error"; 
    }
    return "article_edit";
}


    @PutMapping("/api/article_edit/{id}")
    public String updateArticle(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
        testService.update(id, request);
        return "redirect:/article_list";
    }

    @DeleteMapping("/api/article_delete/{id}")
    public String deleteArticle(@PathVariable Long id) {
        testService.delete(id);
        return "redirect:/article_list";
    }
}
