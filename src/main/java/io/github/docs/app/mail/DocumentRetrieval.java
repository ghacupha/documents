package io.github.docs.app.mail;

import io.github.docs.app.model.FormalDocumentMetadata;
import io.github.docs.app.model.TransactionDocumentMetadata;
import org.springframework.scheduling.annotation.Async;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * This interface retrieves documents from the storage base on the metadata list provided.
 *
 * @param <T>
 */
public interface DocumentRetrieval<T> {

    T transactionDocumentsMap(List<TransactionDocumentMetadata> transactionDocuments);

    T formalDocumentMap(List<FormalDocumentMetadata> formalDocuments);
}
