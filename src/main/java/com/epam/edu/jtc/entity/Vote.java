package com.epam.edu.jtc.entity;

import javax.persistence.*;

/**
 * Created by Alexandr Kozlov on 20.05.2015.
 */
@Entity
@Table(name = "VOTE_TABLE")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "VOTE_ID")
    private long voteID;

    @Column(name = "VOTE_COURSE_ID", nullable = false)
    private long courseID;

    @Column(name = "VOTE_DECISION", nullable = false)
    private String voteDecision;

    @Column(name = "VOTE_MANAGER_ROLE", nullable = false)
    private String voteManagerRole;

    @Column(name = "VOTE_REASON", nullable = false)
    private String voteReason;

    public Vote() {
    }

    public Vote(long courseID, String voteDecision, String voteManagerRole, String voteReason) {
        this.courseID = courseID;
        this.voteDecision = voteDecision;
        this.voteManagerRole = voteManagerRole;
        this.voteReason = voteReason;
    }

    public Vote(long courseID, String voteDecision, String voteManagerRole) {
        this.courseID = courseID;
        this.voteDecision = voteDecision;
        this.voteManagerRole = voteManagerRole;
    }

    public long getVoteID() {
        return voteID;
    }

    public void setVoteID(long voteID) {
        this.voteID = voteID;
    }

    public long getCourseID() {
        return courseID;
    }

    public void setCourseID(long courseID) {
        this.courseID = courseID;
    }

    public String getVoteDecision() {
        return voteDecision;
    }

    public void setVoteDecision(String voteDecision) {
        this.voteDecision = voteDecision;
    }

    public String getVoteManagerRole() {
        return voteManagerRole;
    }

    public void setVoteManagerRole(String voteManagerRole) {
        this.voteManagerRole = voteManagerRole;
    }

    public String getVoteReason() {
        return voteReason;
    }

    public void setVoteReason(String voteReason) {
        this.voteReason = voteReason;
    }
}
