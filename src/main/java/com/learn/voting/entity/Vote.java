package com.learn.voting.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String candidate;
    @ManyToOne
    private Voter voter;

    private Instant timestamp;

    public Vote(String candidate, Voter voter, Instant timestamp) {
        this.candidate = candidate;
        this.voter = voter;
        this.timestamp = timestamp;
    }

    public Vote() {

    }

    public String getCandidate() {
        return candidate;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }

    public Voter getVoter() {
        return voter;
    }

    public void setVoter(Voter voter) {
        this.voter = voter;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
