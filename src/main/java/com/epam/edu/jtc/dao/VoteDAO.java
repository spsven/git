package com.epam.edu.jtc.dao;

import com.epam.edu.jtc.entity.Vote;

import java.util.List;

/**
 * Created by Alexandr Kozlov on 20.05.2015.
 */
public interface VoteDAO {

    void addVote(Vote vote);

    List<Vote> findVoteByCourseID(long courseID);

    void deleteVote(long courseID);

}
