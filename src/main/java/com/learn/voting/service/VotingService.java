package com.learn.voting.service;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.PrivateKey;
import com.hedera.hashgraph.sdk.TransactionResponse;
import com.hedera.hashgraph.sdk.TopicId;
import com.hedera.hashgraph.sdk.TopicMessageSubmitTransaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VotingService {

    @Value("${spring.hedera.accountId}")
    private String accountId;

    @Value("${spring.hedera.privateKey}")
    private String privateKey;

    public void recordVoteOnHedera(String voterId, String candidate) {
        Client client = Client.forTestnet();
        client.setOperator(AccountId.fromString(accountId), PrivateKey.fromString(privateKey));

        // Create a transaction to submit the vote to Hedera
        try {
            TransactionResponse txResponse = new TopicMessageSubmitTransaction()
                    .setTopicId(TopicId.fromString("0.0.3986248"))
                    .setMessage("Voter: " + voterId + " voted for: " + candidate)
                    .execute(client);

            System.out.println("Vote submitted on Hedera. Transaction ID: " + txResponse.transactionId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to submit vote on Hedera.");
        }
    }
}
