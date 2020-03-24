import { element, by, ElementFinder } from 'protractor';

export class SchemeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-scheme div table .btn-danger'));
  title = element.all(by.css('gha-scheme div h2#page-heading span')).first();
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

export class SchemeUpdatePage {
  pageTitle = element(by.id('gha-scheme-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  schemeNameInput = element(by.id('field_schemeName'));
  schemeCodeInput = element(by.id('field_schemeCode'));
  schemeDescriptionInput = element(by.id('field_schemeDescription'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setSchemeNameInput(schemeName: string): Promise<void> {
    await this.schemeNameInput.sendKeys(schemeName);
  }

  async getSchemeNameInput(): Promise<string> {
    return await this.schemeNameInput.getAttribute('value');
  }

  async setSchemeCodeInput(schemeCode: string): Promise<void> {
    await this.schemeCodeInput.sendKeys(schemeCode);
  }

  async getSchemeCodeInput(): Promise<string> {
    return await this.schemeCodeInput.getAttribute('value');
  }

  async setSchemeDescriptionInput(schemeDescription: string): Promise<void> {
    await this.schemeDescriptionInput.sendKeys(schemeDescription);
  }

  async getSchemeDescriptionInput(): Promise<string> {
    return await this.schemeDescriptionInput.getAttribute('value');
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

export class SchemeDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-scheme-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-scheme'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
