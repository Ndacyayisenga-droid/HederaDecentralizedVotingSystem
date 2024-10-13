package com.learn.voting.controller;

import com.learn.voting.entity.Vote;
import com.learn.voting.entity.Voter;
import com.learn.voting.repository.VoteRepository;
import com.learn.voting.repository.VoterRepository;
import com.learn.voting.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/votes")
public class VotingController {

    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private VotingService votingService;

    @PostMapping("/register")
    public ResponseEntity<?> registerVoter(@RequestBody Voter voter) {
        if (voterRepository.findByEmail(voter.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email is already registered");
        }

        // Generate unique voterId
        voter.setVoterId(UUID.randomUUID().toString());
        voterRepository.save(voter);
        return ResponseEntity.ok("Voter registered successfully with Voter ID: " + voter.getVoterId());
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitVote(@RequestParam String voterId, @RequestParam String candidate) {
        Optional<Voter> voterOpt = voterRepository.findByVoterId(voterId);

        if (!voterOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Voter not found");
        }

        Voter voter = voterOpt.get();
        if (voter.isHasVoted()) {
            return ResponseEntity.badRequest().body("You have already voted.");
        }

        // Save the vote to the database
        Vote vote = new Vote();
        vote.setCandidate(candidate);
        vote.setVoter(voter);
        vote.setTimestamp(Instant.now());
        voteRepository.save(vote);

        // Record vote on Hedera
        votingService.recordVoteOnHedera(voter.getVoterId(), candidate);

        // Update voter status
        voter.setHasVoted(true);
        voterRepository.save(voter);

        return ResponseEntity.ok("Vote submitted successfully.");
    }

    @GetMapping("/results")
    public ResponseEntity<?> getResults() {
        List<Vote> allVotes = voteRepository.findAll();
        Map<String, Long> results = allVotes.stream()
                .collect(Collectors.groupingBy(Vote::getCandidate, Collectors.counting()));

        return ResponseEntity.ok(results);
    }
}

