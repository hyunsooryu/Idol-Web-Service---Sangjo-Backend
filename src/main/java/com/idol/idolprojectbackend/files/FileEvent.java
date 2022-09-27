package com.idol.idolprojectbackend.files;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class FileEvent{
    private final LocalDateTime eventStartTime;
    private final FileEventType eventType;
}
