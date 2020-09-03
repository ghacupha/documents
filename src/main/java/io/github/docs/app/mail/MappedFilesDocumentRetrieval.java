package io.github.docs.app.mail;

import io.github.docs.app.model.FormalDocumentMetadata;
import io.github.docs.app.model.TransactionDocumentMetadata;
import io.github.docs.service.FormalDocumentService;
import io.github.docs.service.TransactionDocumentService;
import io.github.docs.service.dto.FormalDocumentDTO;
import io.github.docs.service.dto.TransactionDocumentDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * This class implements the document-retrieval interface by returning completable map
 * of filenames matched to respective file
 */
@Service("mappedFilesDocumentRetrieval")
public class MappedFilesDocumentRetrieval implements DocumentRetrieval<CompletableFuture<Map<String, File>>> {

    private final TransactionDocumentService transactionDocumentService;
    private final FormalDocumentService formalDocumentService;

    public MappedFilesDocumentRetrieval(TransactionDocumentService transactionDocumentService, FormalDocumentService formalDocumentService) {
        this.transactionDocumentService = transactionDocumentService;
        this.formalDocumentService = formalDocumentService;
    }

    @Override
    @Async
    public CompletableFuture<Map<String, File>> transactionDocumentsMap(List<TransactionDocumentMetadata> transactionDocuments) {

        final Map<String, File> DOCUMENT_MAP = new HashMap<>();

        transactionDocuments.forEach(metadata -> {

            long documentId = metadata.getId();

            TransactionDocumentDTO documentDTO =
                transactionDocumentService.findOne(documentId).orElseThrow(() -> new IllegalArgumentException("Transaction document id : " + documentId + " not " + "found!!!"));

            File attachment = new File(documentDTO.getFilename());
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(attachment);

                // Writes bytes from the specified byte array to this file output stream
                fos.write(documentDTO.getTransactionAttachment());
            } catch (Exception e) {
                System.out.println("File not found" + e);
            }

            DOCUMENT_MAP.put(documentDTO.getFilename(), attachment);

        });

        return CompletableFuture.completedFuture(DOCUMENT_MAP);
    }

    @Override
    @Async
    public CompletableFuture<Map<String, File>> formalDocumentMap(List<FormalDocumentMetadata> formalDocuments) {

        final Map<String, File> DOCUMENT_MAP = new HashMap<>();

        formalDocuments.forEach(metadata -> {

            long documentId = metadata.getId();

            FormalDocumentDTO documentDTO =
                formalDocumentService.findOne(documentId).orElseThrow(() -> new IllegalArgumentException("Formal document id : " + documentId + " not " + "found!!!"));

            File attachment = new File(documentDTO.getFilename());
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(attachment);

                // Writes bytes from the specified byte array to this file output stream
                fos.write(documentDTO.getDocumentAttachment());
            } catch (Exception e) {
                System.out.println("File not found" + e);
            }

            DOCUMENT_MAP.put(documentDTO.getFilename(), attachment);

        });

        return CompletableFuture.completedFuture(DOCUMENT_MAP);
    }
}
