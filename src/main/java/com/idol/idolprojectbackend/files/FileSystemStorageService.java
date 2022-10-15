package com.idol.idolprojectbackend.files;

import com.idol.idolprojectbackend.cache.EmbeddedCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileSystemStorageService {

    private final EmbeddedCache embeddedCache;
    private final InitialService initialService;
    private final ApplicationEventPublisher eventPublisher;
    private final FileConfig fileConfig;

    public void store(MultipartFile file) throws Exception{
        boolean wellStored = true;
        log.info("=========파일 업로드 진행 시작================");
        Path ROOT_LOCATION = Paths.get(fileConfig.getPath());
        Path destinationFile = ROOT_LOCATION.resolve(Paths.get(file.getOriginalFilename())).normalize().toAbsolutePath();
        if(destinationFile.getParent().equals(ROOT_LOCATION.toAbsolutePath())) {
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                wellStored = false;
                log.error("파일 업로드 에러입니다.");
                throw new Exception(e.getMessage());
            }
        }
        if(wellStored){
            log.info("=========파일 업로드 진행 완료================");
            eventPublisher.publishEvent(new FileEvent(LocalDateTime.now(), FileEventType.UPLOAD));
        }
    }
}
