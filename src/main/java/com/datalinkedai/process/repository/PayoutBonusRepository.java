package com.datalinkedai.process.repository;

import com.datalinkedai.process.domain.PayoutBonus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the PayoutBonus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PayoutBonusRepository extends MongoRepository<PayoutBonus, String> {}
