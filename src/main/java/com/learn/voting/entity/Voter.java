package com.learn.voting.entity;

import jakarta.persistence.*;

@Entity
public class Voter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String voterId;

    @Column(unique = true)
    private String email;

    private boolean hasVoted = false;

    public Voter(String voterId, String email, boolean hasVoted) {
        this.voterId = voterId;
        this.email = email;
        this.hasVoted = hasVoted;
    }

    public Voter() {

    }

    public String getVoterId() {
        return voterId;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isHasVoted() {
        return hasVoted;
    }

    public void setHasVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }
}

