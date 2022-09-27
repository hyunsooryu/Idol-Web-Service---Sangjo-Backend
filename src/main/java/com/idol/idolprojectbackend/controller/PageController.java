package com.idol.idolprojectbackend.controller;

import com.idol.idolprojectbackend.files.FileSystemStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileSystems;

@CrossOrigin
@RequestMapping("/files")
@RequiredArgsConstructor
@Controller
public class PageController {

    private final FileSystemStorageService fileSystemStorageService;

    @ResponseBody
    @PostMapping(path = "/upload")
    public String submit(@RequestParam("file") MultipartFile file){
        fileSystemStorageService.store(file);
        return "hello";
    }
}
