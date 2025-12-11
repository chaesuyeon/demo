package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Controller
public class FileUploadController {

    // 업로드 폼 이동
    @GetMapping("/upload_form")
    public String uploadForm() {
        return "upload_form";
    }

    // 업로드 처리
    @PostMapping("/upload")
    public String fileUpload(
            @RequestParam("file1") MultipartFile file1,
            @RequestParam("file2") MultipartFile file2,
            Model model) {

        try {
            String saved1 = saveFile(file1);
            String saved2 = saveFile(file2);

            model.addAttribute("file1", saved1);
            model.addAttribute("file2", saved2);

            return "upload_success";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "upload_error";
        }
    }

    // 파일 저장 처리
    private String saveFile(MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new IOException("파일이 비어 있습니다.");
        }

        // 랜덤 이름 + 원본 파일명
        String newName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // 저장 폴더
        Path uploadPath = Paths.get("uploads");

        if (!Files.exists(uploadPath)) {
            Files.createDirectory(uploadPath);
        }

        // 파일 저장
        Path filePath = uploadPath.resolve(newName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return newName;
    }

}

