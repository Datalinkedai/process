package com.datalinkedai.process.repository;

import com.datalinkedai.process.domain.Payout;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Payout entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PayoutRepository extends MongoRepository<Payout, String> {}
