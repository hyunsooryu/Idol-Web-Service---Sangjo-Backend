package com.idol.idolprojectbackend.response;

import com.idol.idolprojectbackend.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberDTO {
    private Long memberId;
    private Long groupId;
    private String name;
    private String thumbnailImgUrl;
    private String groupName;


    public static MemberDTO convertFromMember(Member member){
        if(Objects.isNull(member))return new MemberDTO();
        return new MemberDTO(member.getId(), member.getTeam().getId(), member.getName(), member.getImgPath(), member.getTeam().getName());
    }

    public static List<MemberDTO> convertFromMembers(List<Member> members){
        if(CollectionUtils.isEmpty(members)) return Collections.EMPTY_LIST;
        return members.stream()
                .map(m->{
                    return new MemberDTO(m.getId(), m.getTeam().getId(),m.getName(), m.getImgPath(), m.getTeam().getName());
                }).collect(Collectors.toList());
    }
}
