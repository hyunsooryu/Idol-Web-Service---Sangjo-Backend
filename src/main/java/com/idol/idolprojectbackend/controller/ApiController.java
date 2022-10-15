package com.idol.idolprojectbackend.controller;


import com.idol.idolprojectbackend.cache.EmbeddedCache;
import com.idol.idolprojectbackend.domain.Member;
import com.idol.idolprojectbackend.domain.MemberRepository;
import com.idol.idolprojectbackend.domain.TeamRepository;
import com.idol.idolprojectbackend.response.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class ApiController {

    private final MemberRepository memberRepository;

    private final EmbeddedCache embeddedCache;

    private final TeamRepository teamRepository;

    @GetMapping(path = "/member")
    public List<MemberDTO> getMembersByName(@RequestParam("name") String memberName){
       return MemberDTO.convertFromMembers(memberRepository.findMembersByName(memberName));
    }

    @GetMapping(path = "/member/{id}")
    public MemberDTO getMemberById(@PathVariable Long id){
        return MemberDTO.convertFromMember(memberRepository.findMemberById(id));
    }

    @GetMapping(path = "/team")
    public List<MemberDTO> getMembersByTeamName(@RequestParam("team-name") String teamName){
        return MemberDTO.convertFromMembers(teamRepository.findMembersByTeamName(teamName));
    }

    @GetMapping(path = "/member/search")
    public List<MemberDTO> getMemberByNameOrTeamName(@RequestParam("key") String searchKey){
        return MemberDTO.convertFromMembers(embeddedCache.get(searchKey));
    }

    @GetMapping(path = "/member/page")
    public List<MemberDTO> getMemberByPagination(@RequestParam("pageNo") Integer pageNo){
        return MemberDTO.convertFromMembers(memberRepository.findMemberByPageOffset((pageNo - 1) * 25));
    }

    @PostMapping(path = "/member")
    public List<MemberDTO> getMemberByIds(@RequestBody List<Long> ids){
        return MemberDTO.convertFromMembers(memberRepository.findMemberByIdList(ids));
    }



}
