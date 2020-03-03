import { element, by, ElementFinder } from 'protractor';

export class DepartmentComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-department div table .btn-danger'));
  title = element.all(by.css('gha-department div h2#page-heading span')).first();
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

export class DepartmentUpdatePage {
  pageTitle = element(by.id('gha-department-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  departmentNameInput = element(by.id('field_departmentName'));
  departmentNumberInput = element(by.id('field_departmentNumber'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setDepartmentNameInput(departmentName: string): Promise<void> {
    await this.departmentNameInput.sendKeys(departmentName);
  }

  async getDepartmentNameInput(): Promise<string> {
    return await this.departmentNameInput.getAttribute('value');
  }

  async setDepartmentNumberInput(departmentNumber: string): Promise<void> {
    await this.departmentNumberInput.sendKeys(departmentNumber);
  }

  async getDepartmentNumberInput(): Promise<string> {
    return await this.departmentNumberInput.getAttribute('value');
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

export class DepartmentDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-department-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-department'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
