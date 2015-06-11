package com.epam.edu.jtc.dao;

import com.epam.edu.jtc.entity.Vote;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alexandr Kozlov on 20.05.2015.
 */
@Repository
public class VoteDAOImpl implements VoteDAO {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    @SuppressWarnings("unchecked")
    public void addVote(Vote vote) {
        sessionFactory.getCurrentSession().save(vote);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Vote> findVoteByCourseID(long courseID) {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Vote.class)
                .add(Restrictions.eq("courseID", courseID));

        return criteria.list();
    }


    @Override
    @SuppressWarnings("unchecked")
    public void deleteVote(long courseID) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Vote.class)
                .add(Restrictions.eq("courseID", courseID));

        List<Vote> voteList = criteria.list();

        for (Vote aVoteList : voteList) {
            sessionFactory.getCurrentSession().delete(aVoteList);
        }
    }
}
