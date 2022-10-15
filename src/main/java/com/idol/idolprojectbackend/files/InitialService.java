package com.idol.idolprojectbackend.files;

import com.idol.idolprojectbackend.domain.Member;
import com.idol.idolprojectbackend.domain.Team;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Timer;

@Slf4j
@Repository
@RequiredArgsConstructor
public class InitialService {

    private final FileConfig fileConfig;

    @PersistenceContext
    private EntityManager em;

    private final ResourceLoader resourceLoader;

    public Resource getCsvFiles(){
        Resource fileResource = resourceLoader.getResource(fileConfig.getCsv());
        return fileResource;
    }

    @EventListener(FileEvent.class)
    @Transactional
    public void updateDataBaseFromCsv(FileEvent event){
        if(!event.getEventType().equals(FileEventType.UPLOAD)){
            return;
        }

        String csvPath = fileConfig.getCsv();
        log.info("====update idol data========");
        Resource resource = resourceLoader.getResource(csvPath);
        try{
            Path path = Paths.get(resource.getURI());
            List<String> lines = Files.readAllLines(path);
            int n = 1;
            int randomNum = 1;
            String imgUrl = "https://loremflickr.com/320/240?random=";
            Team team = null;
            int lineNo = 0;
            for(String line : lines) {
                if(lineNo == 0){
                    lineNo++;
                    continue;
                }
                String[] temps = line.split(",");
                System.out.println(line);
                if (temps[0].equals(String.valueOf(n))) { //team
                    n += 1;
                    team = new Team(temps[1]);
                    //em.persist(team);
                    List<Team> foundTeams = em.createQuery("select t from Team as t where t.name = :name")
                            .setParameter("name", temps[1]).getResultList();
                    if(CollectionUtils.isEmpty(foundTeams)){
                        log.info("신규 팀 추가 - " + team.getName());
                        em.persist(team);
                    }else{
                        log.info(team.getName() + "은 이미 존재하여 인서트 하지 않음");
                        team = foundTeams.get(0);
                    }
                    Member member = new Member(temps[3], imgUrl + randomNum);
                    List<Member> members = em.createQuery("select m from Member as m where m.name = :name")
                            .setParameter("name", temps[3]).getResultList();
                    if(CollectionUtils.isEmpty(members)){
                        log.info("신규 멤버추가 " + member.getName() + " | " + member.getImgPath());
                        member.changeTeam(team);
                        em.persist(member);
                    }else{
                        Member foundMember = members.get(0);
                        log.info("기존 멤버 업데이트 - imgUrl");
                        foundMember.updateWithFileSystem(imgUrl + randomNum);
                    }

                } else {//member
                    Member member = new Member(temps[3], imgUrl + randomNum);
                    List<Member> members = em.createQuery("select m from Member as m where m.name = :name")
                            .setParameter("name", temps[3]).getResultList();
                    if(CollectionUtils.isEmpty(members)){
                        log.info("신규 멤버추가 " + member.getName() + " | " + member.getImgPath());
                        member.changeTeam(team);
                        em.persist(member);
                    }else{
                        Member foundMember = members.get(0);
                        log.info("기존 멤버 업데이트 - imgUrl");
                        foundMember.updateWithFileSystem(imgUrl + randomNum);
                    }
                }
                randomNum++;
                lineNo++;
            }


        }catch (Exception e){
            log.error("{}", e.getMessage(), e);
        }
    }

    @Transactional
    public void initDataBaseFromCsv(){

        String csvPath = fileConfig.getCsv();

        log.info("====init idol data========");
        LocalDateTime startTime = LocalDateTime.now();

        Resource resource = resourceLoader.getResource(csvPath);

        try{

            Path path = Paths.get(resource.getURI());
            List<String> lines = Files.readAllLines(path);
            int n = 1;
            int randomNum = 1;
            String imgUrl = "https://loremflickr.com/320/240?random=";
            Team team = null;
            int lineNo = 0;
            for(String line : lines) {
                if(lineNo == 0){
                    lineNo++;
                    continue;
                }
                String[] temps = line.split(",");
                System.out.println(line);
                if (temps[0].equals(String.valueOf(n))) { //team
                    n += 1;
                    team = new Team(temps[1]);
                    em.persist(team);
                    Member member = new Member(temps[3], imgUrl + randomNum);
                    member.changeTeam(team);
                    em.persist(member);
                } else {//member
                    Member member = new Member(temps[3], imgUrl + randomNum);
                    member.changeTeam(team);
                    em.persist(member);
                }
                randomNum++;
                lineNo++;
            }


        }catch (Exception e){
            log.error("{}", e.getMessage(), e);
        }
    }
}
