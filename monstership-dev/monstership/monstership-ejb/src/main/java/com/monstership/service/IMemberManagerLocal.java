package com.monstership.service;

import com.monstership.model.Member;

import javax.ejb.Local;

/**
 * Created by maxisoft on 26/01/2016.
 */
@Local
public interface IMemberManagerLocal {
    Member connect(Member member) throws Exception;

    Member register(Member member) throws Exception;
}
