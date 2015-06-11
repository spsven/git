package com.epam.edu.jtc.service;

import com.epam.edu.jtc.dao.VoteDAO;
import com.epam.edu.jtc.entity.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Alexandr Kozlov on 20.05.2015.
 */
@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteDAO voteDAO;

    @Override
    @Transactional
    public void addVote(Vote vote) {
        voteDAO.addVote(vote);
    }

    @Override
    @Transactional
    public List<Vote> findVoteByCourseID(long courseID) {
        return voteDAO.findVoteByCourseID(courseID);
    }

    @Override
    @Transactional
    public void deleteVote(long courseID) {
        voteDAO.deleteVote(courseID);
    }
}
