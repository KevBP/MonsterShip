package com.monstership.service;

import com.monstership.model.Member;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.logging.Logger;

//The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class MemberManager {

    @Inject
    private Logger log;
    @Inject
    private EntityManager em;
    @Inject
    private Event<Member> memberEventSrc;

    public void connect(Member member) throws Exception {
        Query q = em.createQuery("from Member m where m.pseudo=:ps and m.password=:pw");
        q.setParameter("ps", member.getPseudo());
        q.setParameter("pw", member.getPassword());
        if(q.getResultList().isEmpty()) {
            log.info("ERROR Connecting " + member);
            throw new Exception();
        }
        log.info("Connecting " + member);
    }

    public void register(Member member) throws Exception {
        log.info("Registering " + member);
        em.persist(member);
        memberEventSrc.fire(member);
    }
}
