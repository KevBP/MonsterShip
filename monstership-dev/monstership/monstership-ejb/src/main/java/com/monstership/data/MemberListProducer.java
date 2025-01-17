package com.monstership.data;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

import com.monstership.model.Member;

@RequestScoped
public class MemberListProducer {

    @Inject
    private MemberRepository memberRepository;

    private List<Member> members;

    // @Named provides access the return value via the EL variable name "members" in the UI (e.g.,
    // Facelets or JSP view)
    @Produces
    @Named
    public List<Member> getMembers() {
        return members;
    }

    public void onMemberListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Member member) {
        retrieveAllMembersOrderedByPseudo();
    }

    @PostConstruct
    public void retrieveAllMembersOrderedByPseudo() {
        members = memberRepository.findAllOrderedByPseudo();
    }
}
