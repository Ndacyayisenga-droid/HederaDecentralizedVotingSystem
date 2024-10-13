package com.learn.voting.repository;

import com.learn.voting.entity.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoterRepository extends JpaRepository<Voter, Long> {
    Optional<Voter> findByEmail(String email);
    Optional<Voter> findByVoterId(String voterId);
}

