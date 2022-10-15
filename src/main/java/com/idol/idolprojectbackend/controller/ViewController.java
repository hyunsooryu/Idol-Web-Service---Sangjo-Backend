package com.idol.idolprojectbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idol.idolprojectbackend.dto.*;
import com.idol.idolprojectbackend.files.InitialService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ViewController {

    private final InitialService initialService;

    @Value("${user.id}")
    private String userId;

    @Value("${user.pw}")
    private String userPw;


    @GetMapping(path = {"/file"})
    public void getCsvFile(HttpServletResponse response, HttpSession session) throws IOException {
        if(Objects.isNull(session.getAttribute("user"))){
            response.setStatus(400);
            return;
        }
        File file = initialService.getCsvFiles().getFile();
        String filename = file.getName();
        String mimeType = "application/octet-stream";
        response.setContentType(mimeType);	//--- reponse에 mimetype을 설정합니다.
        response.setHeader("Content-Disposition", "attachment; filename=\""+filename+"\"");		//--- Content-Disposition를 attachment로 설정하여 다운로드 받을 파일임을 브라우저에게 알려줍니다.
        response.setContentLength((int)file.length());		//--- response content length를 설정합니다.
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));	//--- inputstream 객체를 얻습니다.
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

    @GetMapping(path = {"", "/login"})
    public String login(Model model, HttpSession session){
        if(!Objects.isNull(session.getAttribute("user"))){
            LoginUser loginUser = (LoginUser) session.getAttribute("user");
            RenderJson renderJson = new RenderJson();
            renderJson.put("isLogin", true);
            model.addAttribute(renderJson);
            return "login";
        }

        RenderJson renderJson = new RenderJson();
        renderJson.put("id", userId);
        model.addAttribute(renderJson);
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(HttpSession session, @RequestBody User loginUser){
        if(!isValidUser(loginUser)){
            throw new LoginException(ErrorCode.ERR_001);
        }
        LoginUser user = new LoginUser();
        user.setId(loginUser.getId());
        session.setAttribute("user", user);
        return "GOOD";
    }

    private boolean isValidUser(User user){
        return userId.equals(user.getId()) && userPw.equals(user.getPw());
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<Map<String,String>> loginExceptionHandler(LoginException exception){
        return ResponseEntity.status(exception.getErrorCode().getStatus())
                .body(Collections.singletonMap("msg", exception.getErrorCode().getMsg()));
    }

}
