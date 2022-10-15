package com.idol.idolprojectbackend.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public enum ErrorCode {
    ERR_001("비밀번호가 틀렸습니다!", HttpStatus.BAD_REQUEST);
    String msg;
    HttpStatus status;

    ErrorCode(String msg, HttpStatus status){
            this.msg = msg;
            this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
