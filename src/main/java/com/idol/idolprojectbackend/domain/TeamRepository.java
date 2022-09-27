package com.idol.idolprojectbackend.domain;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TeamRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Member> findMembersByTeamName(String teamName){
        List<Member> members = em.createQuery("select m from Member as m join fetch m.team as t where t.name = :name").setParameter("name", teamName)
                .getResultList();

        return members;
    }
    public Team findTeamById(Long id){
        return em.find(Team.class, id);
    }
}
