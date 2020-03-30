import { element, by, ElementFinder } from 'protractor';

export class FormalDocumentComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-formal-document div table .btn-danger'));
  title = element.all(by.css('gha-formal-document div h2#page-heading span')).first();
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

export class FormalDocumentUpdatePage {
  pageTitle = element(by.id('gha-formal-document-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  documentTitleInput = element(by.id('field_documentTitle'));
  documentSubjectInput = element(by.id('field_documentSubject'));
  briefDescriptionInput = element(by.id('field_briefDescription'));
  documentDateInput = element(by.id('field_documentDate'));
  documentTypeSelect = element(by.id('field_documentType'));
  documentStandardNumberInput = element(by.id('field_documentStandardNumber'));
  documentAttachmentInput = element(by.id('file_documentAttachment'));
  filenameInput = element(by.id('field_filename'));

  schemesSelect = element(by.id('field_schemes'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setDocumentTitleInput(documentTitle: string): Promise<void> {
    await this.documentTitleInput.sendKeys(documentTitle);
  }

  async getDocumentTitleInput(): Promise<string> {
    return await this.documentTitleInput.getAttribute('value');
  }

  async setDocumentSubjectInput(documentSubject: string): Promise<void> {
    await this.documentSubjectInput.sendKeys(documentSubject);
  }

  async getDocumentSubjectInput(): Promise<string> {
    return await this.documentSubjectInput.getAttribute('value');
  }

  async setBriefDescriptionInput(briefDescription: string): Promise<void> {
    await this.briefDescriptionInput.sendKeys(briefDescription);
  }

  async getBriefDescriptionInput(): Promise<string> {
    return await this.briefDescriptionInput.getAttribute('value');
  }

  async setDocumentDateInput(documentDate: string): Promise<void> {
    await this.documentDateInput.sendKeys(documentDate);
  }

  async getDocumentDateInput(): Promise<string> {
    return await this.documentDateInput.getAttribute('value');
  }

  async setDocumentTypeSelect(documentType: string): Promise<void> {
    await this.documentTypeSelect.sendKeys(documentType);
  }

  async getDocumentTypeSelect(): Promise<string> {
    return await this.documentTypeSelect.element(by.css('option:checked')).getText();
  }

  async documentTypeSelectLastOption(): Promise<void> {
    await this.documentTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setDocumentStandardNumberInput(documentStandardNumber: string): Promise<void> {
    await this.documentStandardNumberInput.sendKeys(documentStandardNumber);
  }

  async getDocumentStandardNumberInput(): Promise<string> {
    return await this.documentStandardNumberInput.getAttribute('value');
  }

  async setDocumentAttachmentInput(documentAttachment: string): Promise<void> {
    await this.documentAttachmentInput.sendKeys(documentAttachment);
  }

  async getDocumentAttachmentInput(): Promise<string> {
    return await this.documentAttachmentInput.getAttribute('value');
  }

  async setFilenameInput(filename: string): Promise<void> {
    await this.filenameInput.sendKeys(filename);
  }

  async getFilenameInput(): Promise<string> {
    return await this.filenameInput.getAttribute('value');
  }

  async schemesSelectLastOption(): Promise<void> {
    await this.schemesSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async schemesSelectOption(option: string): Promise<void> {
    await this.schemesSelect.sendKeys(option);
  }

  getSchemesSelect(): ElementFinder {
    return this.schemesSelect;
  }

  async getSchemesSelectedOption(): Promise<string> {
    return await this.schemesSelect.element(by.css('option:checked')).getText();
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

export class FormalDocumentDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-formalDocument-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-formalDocument'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
