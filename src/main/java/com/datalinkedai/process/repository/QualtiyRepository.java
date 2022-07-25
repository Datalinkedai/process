package com.datalinkedai.process.repository;

import com.datalinkedai.process.domain.Qualtiy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Qualtiy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QualtiyRepository extends MongoRepository<Qualtiy, String> {}
