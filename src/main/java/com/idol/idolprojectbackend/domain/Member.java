package com.idol.idolprojectbackend.domain;

import com.idol.idolprojectbackend.domain.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@SequenceGenerator(sequenceName = "MEMBER_SEQ", allocationSize = 1, initialValue = 1, name = "memberSeq")
@Table(name = "MEMBER", indexes = @Index(columnList = "NAME"))
@Getter
@NoArgsConstructor
public class Member extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "memberSeq")
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "IMG_PATH")
    private String imgPath;

    @Column(name = "LONG_IMG_PATH")
    private String longImgPath;

    @JoinColumn(name = "TEAM_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    public Member(String name, String imgPath, String longImgPath){
        this.name = name;
        this.imgPath = imgPath;
        this.longImgPath = longImgPath;
    }


    public void changeTeam(Team team){
        team.addMember(this);
        this.team = team;
    }

    public void updateWithFileSystem(String imgPath){
        this.imgPath = imgPath;
    }

}
