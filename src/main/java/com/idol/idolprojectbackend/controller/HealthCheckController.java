package com.idol.idolprojectbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class HealthCheckController {

    @GetMapping(path = "/")
    public void goToSpa(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.sendRedirect("https://bemyidol.vercel.app/");
    }

    @GetMapping(path = "/health-check")
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok("OK");
    }
}
