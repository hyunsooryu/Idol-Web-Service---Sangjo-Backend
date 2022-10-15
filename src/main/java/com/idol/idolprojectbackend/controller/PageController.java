package com.idol.idolprojectbackend.controller;

import com.idol.idolprojectbackend.dto.RenderJson;
import com.idol.idolprojectbackend.files.FileSystemStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileSystems;
import java.util.Objects;

@CrossOrigin
@RequestMapping("/files")
@RequiredArgsConstructor
@Controller
public class PageController {

    private final FileSystemStorageService fileSystemStorageService;

    @PostMapping(path = "/upload")
    public String submit(@RequestParam("file") MultipartFile file, Model model){
        boolean isGood = true;
        if(Objects.isNull(file) || file.isEmpty()){
            model.addAttribute("msg", "파일을 첨부해주세요.");
            isGood = false;
            model.addAttribute("status", "BAD");
            return "upload_pro";
        }
        if(!"idols.csv".equals(file.getOriginalFilename())){
            model.addAttribute("msg", "파일명은 idols.csv 여야 합니다.");
            isGood = false;
            model.addAttribute("status", "BAD");
            return "upload_pro";
        }

        try {
            fileSystemStorageService.store(file);
        }catch (Exception e){
            isGood = false;
            model.addAttribute("msg", e.toString());
        }
        model.addAttribute("status", isGood ? "GOOD" : "BAD");
        return "upload_pro";
    }
}
