package com.monstership.service;

import com.monstership.model.Member;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
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
        log.info("Connecting " + member.getPseudo());
        // TODO
    }

    public void register(Member member) throws Exception {
        log.info("Registering " + member.getPseudo());
        em.persist(member);
        memberEventSrc.fire(member);
    }
}
