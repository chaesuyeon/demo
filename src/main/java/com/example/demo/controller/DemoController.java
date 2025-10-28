// DemoController.java
package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

    @GetMapping("/")            // 홈으로 변경 (중복 제거)
    public String home() {
        return "index";         // templates/index.html
    }

@GetMapping("/about_detailed")
public String aboutDetailed() {
    return "about_detailed";  // templates 폴더 안의 about_detailed.html 열기
}



}