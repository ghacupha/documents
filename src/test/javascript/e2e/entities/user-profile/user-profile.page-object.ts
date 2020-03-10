import { element, by, ElementFinder } from 'protractor';

export class UserProfileComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-user-profile div table .btn-danger'));
  title = element.all(by.css('gha-user-profile div h2#page-heading span')).first();
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

export class UserProfileUpdatePage {
  pageTitle = element(by.id('gha-user-profile-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  staffNumberInput = element(by.id('field_staffNumber'));

  userSelect = element(by.id('field_user'));
  departmentSelect = element(by.id('field_department'));
  formalDocumentsSelect = element(by.id('field_formalDocuments'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setStaffNumberInput(staffNumber: string): Promise<void> {
    await this.staffNumberInput.sendKeys(staffNumber);
  }

  async getStaffNumberInput(): Promise<string> {
    return await this.staffNumberInput.getAttribute('value');
  }

  async userSelectLastOption(): Promise<void> {
    await this.userSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async userSelectOption(option: string): Promise<void> {
    await this.userSelect.sendKeys(option);
  }

  getUserSelect(): ElementFinder {
    return this.userSelect;
  }

  async getUserSelectedOption(): Promise<string> {
    return await this.userSelect.element(by.css('option:checked')).getText();
  }

  async departmentSelectLastOption(): Promise<void> {
    await this.departmentSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async departmentSelectOption(option: string): Promise<void> {
    await this.departmentSelect.sendKeys(option);
  }

  getDepartmentSelect(): ElementFinder {
    return this.departmentSelect;
  }

  async getDepartmentSelectedOption(): Promise<string> {
    return await this.departmentSelect.element(by.css('option:checked')).getText();
  }

  async formalDocumentsSelectLastOption(): Promise<void> {
    await this.formalDocumentsSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async formalDocumentsSelectOption(option: string): Promise<void> {
    await this.formalDocumentsSelect.sendKeys(option);
  }

  getFormalDocumentsSelect(): ElementFinder {
    return this.formalDocumentsSelect;
  }

  async getFormalDocumentsSelectedOption(): Promise<string> {
    return await this.formalDocumentsSelect.element(by.css('option:checked')).getText();
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

export class UserProfileDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-userProfile-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-userProfile'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
