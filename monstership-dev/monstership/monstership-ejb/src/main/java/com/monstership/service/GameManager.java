package com.monstership.service;

import com.monstership.model.Member;

import javax.ejb.Stateful;

@Stateful
public class GameManager {;

    private Member member;

    public GameManager() {
    }

    public GameManager(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }
}
