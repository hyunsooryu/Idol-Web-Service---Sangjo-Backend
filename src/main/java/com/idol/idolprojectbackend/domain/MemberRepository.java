package com.idol.idolprojectbackend.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Member> findMembersByName(String memberName){
        List<Member> members = em.createQuery("select m from Member m join fetch m.team t where m.name = :name")
                .setParameter("name", memberName).getResultList();
        return members;
    }

    public Member findMemberById(Long memberId){
        return em.find(Member.class, memberId);
    }

    public List<Member> findMemberBySearchKey(String searchKey){
        List<Member> members = em.createQuery("select m from Member m join fetch m.team t where m.name = :member_name or t.name = :team_name")
                .setParameter("member_name", searchKey)
                .setParameter("team_name", searchKey)
                .getResultList();

        return members;
    }

    public List<Member> findMemberByPageOffset(int startPos){
        List<Member> members =  em.createQuery("select m from Member as m join fetch m.team")
                .setFirstResult(startPos)
                .setMaxResults(10).getResultList();
        return members;
    }

}
