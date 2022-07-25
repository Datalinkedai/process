package com.datalinkedai.process.service.impl;

import com.datalinkedai.process.domain.Documents;
import com.datalinkedai.process.repository.DocumentsRepository;
import com.datalinkedai.process.service.DocumentsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Documents}.
 */
@Service
public class DocumentsServiceImpl implements DocumentsService {

    private final Logger log = LoggerFactory.getLogger(DocumentsServiceImpl.class);

    private final DocumentsRepository documentsRepository;

    public DocumentsServiceImpl(DocumentsRepository documentsRepository) {
        this.documentsRepository = documentsRepository;
    }

    @Override
    public Documents save(Documents documents) {
        log.debug("Request to save Documents : {}", documents);
        return documentsRepository.save(documents);
    }

    @Override
    public Documents update(Documents documents) {
        log.debug("Request to save Documents : {}", documents);
        return documentsRepository.save(documents);
    }

    @Override
    public Optional<Documents> partialUpdate(Documents documents) {
        log.debug("Request to partially update Documents : {}", documents);

        return documentsRepository
            .findById(documents.getId())
            .map(existingDocuments -> {
                if (documents.getDocumentType() != null) {
                    existingDocuments.setDocumentType(documents.getDocumentType());
                }
                if (documents.getDocument() != null) {
                    existingDocuments.setDocument(documents.getDocument());
                }
                if (documents.getDocumentContentType() != null) {
                    existingDocuments.setDocumentContentType(documents.getDocumentContentType());
                }
                if (documents.getDocumentLink() != null) {
                    existingDocuments.setDocumentLink(documents.getDocumentLink());
                }
                if (documents.getDocumentExpiry() != null) {
                    existingDocuments.setDocumentExpiry(documents.getDocumentExpiry());
                }
                if (documents.getVerified() != null) {
                    existingDocuments.setVerified(documents.getVerified());
                }

                return existingDocuments;
            })
            .map(documentsRepository::save);
    }

    @Override
    public List<Documents> findAll() {
        log.debug("Request to get all Documents");
        return documentsRepository.findAll();
    }

    @Override
    public Optional<Documents> findOne(String id) {
        log.debug("Request to get Documents : {}", id);
        return documentsRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Documents : {}", id);
        documentsRepository.deleteById(id);
    }
}
