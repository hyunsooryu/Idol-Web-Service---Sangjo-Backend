package com.idol.idolprojectbackend.cache;

import com.idol.idolprojectbackend.domain.Member;
import com.idol.idolprojectbackend.domain.MemberRepository;
import com.idol.idolprojectbackend.files.FileEvent;
import com.idol.idolprojectbackend.files.FileEventType;
import com.idol.idolprojectbackend.response.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmbeddedCache {
    private final ConcurrentMap<String, List<Member>> cache = new ConcurrentHashMap<>();
    private final MemberRepository memberRepository;

    @EventListener(FileEvent.class)
    public void removeAll(FileEvent event){
        if(!event.getEventType().equals(FileEventType.UPLOAD)){
            return;
        }
        log.info("=====CSV 파일 업로드로 인한 캐시 데이터 FLUSH ALL 진행 시작======== : " + LocalDateTime.now());
        Set keys = new HashSet<>(cache.keySet());
        keys.forEach(cache::remove);
        log.info("=====CSV 파일 업로드로 인한 캐시 데이터 FLUSH ALL 진행 완료======== : " + LocalDateTime.now());
    }

    public List<Member> get(String key){
        List<Member> members = cache.get(key);
        if(!CollectionUtils.isEmpty(members)){
            log.info("cache hit : key = " + key);
            return members;
        }
        log.info("cache miss : key = " + key);
        List<Member> memberList = memberRepository.findMemberBySearchKey(key);
        if(!CollectionUtils.isEmpty(memberList)) {
            log.info("cache save : key = " + key);
            cache.put(key, memberList);
        }
        return memberList;
    }
}
