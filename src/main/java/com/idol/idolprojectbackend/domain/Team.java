package com.idol.idolprojectbackend.domain;

import com.idol.idolprojectbackend.domain.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(sequenceName = "TEAM_SEQ", allocationSize = 1, initialValue = 1, name = "teamSeq")
@Table(name = "TEAM", indexes = @Index(columnList = "NAME"))
@Getter
@NoArgsConstructor
public class Team extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teamSeq")
    @Column(name = "TEAM_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private List<Member> members = new ArrayList<>();

    public Team(String name){
        this.name = name;
    }

    public void addMember(Member member){
        members.add(member);
    }

    public void updateWithFileSystem(String name){
        this.name = name;
    }
}
