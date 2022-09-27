package com.idol.idolprojectbackend;

import com.idol.idolprojectbackend.files.FileConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@RequiredArgsConstructor
public class AppRunner implements ApplicationRunner {

    private final FileConfig fileConfig;

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
