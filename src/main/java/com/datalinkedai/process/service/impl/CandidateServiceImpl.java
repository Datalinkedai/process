package com.datalinkedai.process.service.impl;

import com.datalinkedai.process.domain.Candidate;
import com.datalinkedai.process.repository.CandidateRepository;
import com.datalinkedai.process.service.CandidateService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Candidate}.
 */
@Service
public class CandidateServiceImpl implements CandidateService {

    private final Logger log = LoggerFactory.getLogger(CandidateServiceImpl.class);

    private final CandidateRepository candidateRepository;

    public CandidateServiceImpl(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Override
    public Candidate save(Candidate candidate) {
        log.debug("Request to save Candidate : {}", candidate);
        return candidateRepository.save(candidate);
    }

    @Override
    public Candidate update(Candidate candidate) {
        log.debug("Request to save Candidate : {}", candidate);
        return candidateRepository.save(candidate);
    }

    @Override
    public Optional<Candidate> partialUpdate(Candidate candidate) {
        log.debug("Request to partially update Candidate : {}", candidate);

        return candidateRepository
            .findById(candidate.getId())
            .map(existingCandidate -> {
                if (candidate.getFirstName() != null) {
                    existingCandidate.setFirstName(candidate.getFirstName());
                }
                if (candidate.getLastName() != null) {
                    existingCandidate.setLastName(candidate.getLastName());
                }
                if (candidate.getPhoneNumber() != null) {
                    existingCandidate.setPhoneNumber(candidate.getPhoneNumber());
                }
                if (candidate.getUserName() != null) {
                    existingCandidate.setUserName(candidate.getUserName());
                }
                if (candidate.getEductionQualification() != null) {
                    existingCandidate.setEductionQualification(candidate.getEductionQualification());
                }
                if (candidate.getResumeLink() != null) {
                    existingCandidate.setResumeLink(candidate.getResumeLink());
                }
                if (candidate.getStatus() != null) {
                    existingCandidate.setStatus(candidate.getStatus());
                }

                return existingCandidate;
            })
            .map(candidateRepository::save);
    }

    @Override
    public List<Candidate> findAll() {
        log.debug("Request to get all Candidates");
        return candidateRepository.findAll();
    }

    @Override
    public Optional<Candidate> findOne(String id) {
        log.debug("Request to get Candidate : {}", id);
        return candidateRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Candidate : {}", id);
        candidateRepository.deleteById(id);
    }
}
