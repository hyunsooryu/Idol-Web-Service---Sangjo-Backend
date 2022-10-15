package com.idol.idolprojectbackend.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;


@Getter
@Setter
public class User{
    private String id;
    private String pw;
}