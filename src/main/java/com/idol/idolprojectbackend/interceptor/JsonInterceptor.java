package com.idol.idolprojectbackend.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idol.idolprojectbackend.dto.RenderJson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RequiredArgsConstructor
public class JsonInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if(Objects.isNull(modelAndView) || Objects.isNull(modelAndView.getModel()) ||
        Objects.isNull(modelAndView.getModel().get("renderJson"))) return;
        RenderJson renderJson = (RenderJson) modelAndView.getModel().get("renderJson");
        String json = objectMapper.writeValueAsString(renderJson);
        modelAndView.getModel().put("renderJson", json);

    }
}
