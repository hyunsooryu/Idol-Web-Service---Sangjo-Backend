package com.idol.idolprojectbackend;

import com.idol.idolprojectbackend.files.InitialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


@Slf4j
@SpringBootApplication
@EnableJpaAuditing
@ConfigurationPropertiesScan
@RequiredArgsConstructor
public class IdolProjectBackendApplication {

    private final InitialService initialService;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(IdolProjectBackendApplication.class);
        app.setWebApplicationType(WebApplicationType.SERVLET);
        app.run(args);

    }

    @PostConstruct
    public void initData(){
        initialService.initDataBaseFromCsv();
    }


}
