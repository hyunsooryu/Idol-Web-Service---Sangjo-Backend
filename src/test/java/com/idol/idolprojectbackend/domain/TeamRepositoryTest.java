package com.idol.idolprojectbackend.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
class TeamRepositoryTest {
    @PersistenceContext
    private EntityManager em;

    @Test
    @Transactional
    public void test(){
        em.createQuery("select m from Member as m join fetch m.team")
                .setFirstResult(0)
                .setMaxResults(10);

    }

}