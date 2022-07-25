package com.datalinkedai.process.service.impl;

import com.datalinkedai.process.domain.Qualtiy;
import com.datalinkedai.process.repository.QualtiyRepository;
import com.datalinkedai.process.service.QualtiyService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Qualtiy}.
 */
@Service
public class QualtiyServiceImpl implements QualtiyService {

    private final Logger log = LoggerFactory.getLogger(QualtiyServiceImpl.class);

    private final QualtiyRepository qualtiyRepository;

    public QualtiyServiceImpl(QualtiyRepository qualtiyRepository) {
        this.qualtiyRepository = qualtiyRepository;
    }

    @Override
    public Qualtiy save(Qualtiy qualtiy) {
        log.debug("Request to save Qualtiy : {}", qualtiy);
        return qualtiyRepository.save(qualtiy);
    }

    @Override
    public Qualtiy update(Qualtiy qualtiy) {
        log.debug("Request to save Qualtiy : {}", qualtiy);
        return qualtiyRepository.save(qualtiy);
    }

    @Override
    public Optional<Qualtiy> partialUpdate(Qualtiy qualtiy) {
        log.debug("Request to partially update Qualtiy : {}", qualtiy);

        return qualtiyRepository
            .findById(qualtiy.getId())
            .map(existingQualtiy -> {
                if (qualtiy.getDate() != null) {
                    existingQualtiy.setDate(qualtiy.getDate());
                }
                if (qualtiy.getReWork() != null) {
                    existingQualtiy.setReWork(qualtiy.getReWork());
                }
                if (qualtiy.getReWorkStatus() != null) {
                    existingQualtiy.setReWorkStatus(qualtiy.getReWorkStatus());
                }
                if (qualtiy.getRemarks() != null) {
                    existingQualtiy.setRemarks(qualtiy.getRemarks());
                }
                if (qualtiy.getFileReachDate() != null) {
                    existingQualtiy.setFileReachDate(qualtiy.getFileReachDate());
                }
                if (qualtiy.getQcStartDate() != null) {
                    existingQualtiy.setQcStartDate(qualtiy.getQcStartDate());
                }
                if (qualtiy.getQcEndDate() != null) {
                    existingQualtiy.setQcEndDate(qualtiy.getQcEndDate());
                }
                if (qualtiy.getResult() != null) {
                    existingQualtiy.setResult(qualtiy.getResult());
                }
                if (qualtiy.getReWorkDuration() != null) {
                    existingQualtiy.setReWorkDuration(qualtiy.getReWorkDuration());
                }

                return existingQualtiy;
            })
            .map(qualtiyRepository::save);
    }

    @Override
    public List<Qualtiy> findAll() {
        log.debug("Request to get all Qualtiys");
        return qualtiyRepository.findAll();
    }

    @Override
    public Optional<Qualtiy> findOne(String id) {
        log.debug("Request to get Qualtiy : {}", id);
        return qualtiyRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Qualtiy : {}", id);
        qualtiyRepository.deleteById(id);
    }
}
