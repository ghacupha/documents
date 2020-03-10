import { element, by, ElementFinder } from 'protractor';

export class TransactionDocumentComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-transaction-document div table .btn-danger'));
  title = element.all(by.css('gha-transaction-document div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getText();
  }
}

export class TransactionDocumentUpdatePage {
  pageTitle = element(by.id('gha-transaction-document-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  transactionNumberInput = element(by.id('field_transactionNumber'));
  transactionDateInput = element(by.id('field_transactionDate'));
  briefDescriptionInput = element(by.id('field_briefDescription'));
  justificationInput = element(by.id('field_justification'));
  transactionAmountInput = element(by.id('field_transactionAmount'));
  payeeNameInput = element(by.id('field_payeeName'));
  invoiceNumberInput = element(by.id('field_invoiceNumber'));
  lpoNumberInput = element(by.id('field_lpoNumber'));
  debitNoteNumberInput = element(by.id('field_debitNoteNumber'));
  logisticReferenceNumberInput = element(by.id('field_logisticReferenceNumber'));
  memoNumberInput = element(by.id('field_memoNumber'));
  documentStandardNumberInput = element(by.id('field_documentStandardNumber'));
  transactionAttachmentInput = element(by.id('file_transactionAttachment'));

  documentOwnersSelect = element(by.id('field_documentOwners'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setTransactionNumberInput(transactionNumber: string): Promise<void> {
    await this.transactionNumberInput.sendKeys(transactionNumber);
  }

  async getTransactionNumberInput(): Promise<string> {
    return await this.transactionNumberInput.getAttribute('value');
  }

  async setTransactionDateInput(transactionDate: string): Promise<void> {
    await this.transactionDateInput.sendKeys(transactionDate);
  }

  async getTransactionDateInput(): Promise<string> {
    return await this.transactionDateInput.getAttribute('value');
  }

  async setBriefDescriptionInput(briefDescription: string): Promise<void> {
    await this.briefDescriptionInput.sendKeys(briefDescription);
  }

  async getBriefDescriptionInput(): Promise<string> {
    return await this.briefDescriptionInput.getAttribute('value');
  }

  async setJustificationInput(justification: string): Promise<void> {
    await this.justificationInput.sendKeys(justification);
  }

  async getJustificationInput(): Promise<string> {
    return await this.justificationInput.getAttribute('value');
  }

  async setTransactionAmountInput(transactionAmount: string): Promise<void> {
    await this.transactionAmountInput.sendKeys(transactionAmount);
  }

  async getTransactionAmountInput(): Promise<string> {
    return await this.transactionAmountInput.getAttribute('value');
  }

  async setPayeeNameInput(payeeName: string): Promise<void> {
    await this.payeeNameInput.sendKeys(payeeName);
  }

  async getPayeeNameInput(): Promise<string> {
    return await this.payeeNameInput.getAttribute('value');
  }

  async setInvoiceNumberInput(invoiceNumber: string): Promise<void> {
    await this.invoiceNumberInput.sendKeys(invoiceNumber);
  }

  async getInvoiceNumberInput(): Promise<string> {
    return await this.invoiceNumberInput.getAttribute('value');
  }

  async setLpoNumberInput(lpoNumber: string): Promise<void> {
    await this.lpoNumberInput.sendKeys(lpoNumber);
  }

  async getLpoNumberInput(): Promise<string> {
    return await this.lpoNumberInput.getAttribute('value');
  }

  async setDebitNoteNumberInput(debitNoteNumber: string): Promise<void> {
    await this.debitNoteNumberInput.sendKeys(debitNoteNumber);
  }

  async getDebitNoteNumberInput(): Promise<string> {
    return await this.debitNoteNumberInput.getAttribute('value');
  }

  async setLogisticReferenceNumberInput(logisticReferenceNumber: string): Promise<void> {
    await this.logisticReferenceNumberInput.sendKeys(logisticReferenceNumber);
  }

  async getLogisticReferenceNumberInput(): Promise<string> {
    return await this.logisticReferenceNumberInput.getAttribute('value');
  }

  async setMemoNumberInput(memoNumber: string): Promise<void> {
    await this.memoNumberInput.sendKeys(memoNumber);
  }

  async getMemoNumberInput(): Promise<string> {
    return await this.memoNumberInput.getAttribute('value');
  }

  async setDocumentStandardNumberInput(documentStandardNumber: string): Promise<void> {
    await this.documentStandardNumberInput.sendKeys(documentStandardNumber);
  }

  async getDocumentStandardNumberInput(): Promise<string> {
    return await this.documentStandardNumberInput.getAttribute('value');
  }

  async setTransactionAttachmentInput(transactionAttachment: string): Promise<void> {
    await this.transactionAttachmentInput.sendKeys(transactionAttachment);
  }

  async getTransactionAttachmentInput(): Promise<string> {
    return await this.transactionAttachmentInput.getAttribute('value');
  }

  async documentOwnersSelectLastOption(): Promise<void> {
    await this.documentOwnersSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async documentOwnersSelectOption(option: string): Promise<void> {
    await this.documentOwnersSelect.sendKeys(option);
  }

  getDocumentOwnersSelect(): ElementFinder {
    return this.documentOwnersSelect;
  }

  async getDocumentOwnersSelectedOption(): Promise<string> {
    return await this.documentOwnersSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class TransactionDocumentDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-transactionDocument-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-transactionDocument'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
