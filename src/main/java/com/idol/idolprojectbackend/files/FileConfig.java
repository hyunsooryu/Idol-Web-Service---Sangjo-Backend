package com.idol.idolprojectbackend.files;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;

@ConstructorBinding
@ConfigurationProperties(prefix = "root")
@RequiredArgsConstructor
@Getter
public class FileConfig {
    private final String path;
    private final String csv;
}
