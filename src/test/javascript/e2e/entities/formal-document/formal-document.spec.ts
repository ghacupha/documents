import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FormalDocumentComponentsPage, FormalDocumentDeleteDialog, FormalDocumentUpdatePage } from './formal-document.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('FormalDocument e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let formalDocumentComponentsPage: FormalDocumentComponentsPage;
  let formalDocumentUpdatePage: FormalDocumentUpdatePage;
  let formalDocumentDeleteDialog: FormalDocumentDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load FormalDocuments', async () => {
    await navBarPage.goToEntity('formal-document');
    formalDocumentComponentsPage = new FormalDocumentComponentsPage();
    await browser.wait(ec.visibilityOf(formalDocumentComponentsPage.title), 5000);
    expect(await formalDocumentComponentsPage.getTitle()).to.eq('Formal Documents');
    await browser.wait(
      ec.or(ec.visibilityOf(formalDocumentComponentsPage.entities), ec.visibilityOf(formalDocumentComponentsPage.noResult)),
      1000
    );
  });

  it('should load create FormalDocument page', async () => {
    await formalDocumentComponentsPage.clickOnCreateButton();
    formalDocumentUpdatePage = new FormalDocumentUpdatePage();
    expect(await formalDocumentUpdatePage.getPageTitle()).to.eq('Create or edit a Formal Document');
    await formalDocumentUpdatePage.cancel();
  });

  it('should create and save FormalDocuments', async () => {
    const nbButtonsBeforeCreate = await formalDocumentComponentsPage.countDeleteButtons();

    await formalDocumentComponentsPage.clickOnCreateButton();

    await promise.all([
      formalDocumentUpdatePage.setDocumentTitleInput('documentTitle'),
      formalDocumentUpdatePage.setDocumentSubjectInput('documentSubject'),
      formalDocumentUpdatePage.setBriefDescriptionInput('briefDescription'),
      formalDocumentUpdatePage.setDocumentDateInput('2000-12-31'),
      formalDocumentUpdatePage.documentTypeSelectLastOption(),
      formalDocumentUpdatePage.setDocumentStandardNumberInput('documentStandardNumber'),
      formalDocumentUpdatePage.setDocumentAttachmentInput(absolutePath)
    ]);

    expect(await formalDocumentUpdatePage.getDocumentTitleInput()).to.eq(
      'documentTitle',
      'Expected DocumentTitle value to be equals to documentTitle'
    );
    expect(await formalDocumentUpdatePage.getDocumentSubjectInput()).to.eq(
      'documentSubject',
      'Expected DocumentSubject value to be equals to documentSubject'
    );
    expect(await formalDocumentUpdatePage.getBriefDescriptionInput()).to.eq(
      'briefDescription',
      'Expected BriefDescription value to be equals to briefDescription'
    );
    expect(await formalDocumentUpdatePage.getDocumentDateInput()).to.eq(
      '2000-12-31',
      'Expected documentDate value to be equals to 2000-12-31'
    );
    expect(await formalDocumentUpdatePage.getDocumentStandardNumberInput()).to.eq(
      'documentStandardNumber',
      'Expected DocumentStandardNumber value to be equals to documentStandardNumber'
    );
    expect(await formalDocumentUpdatePage.getDocumentAttachmentInput()).to.endsWith(
      fileNameToUpload,
      'Expected DocumentAttachment value to be end with ' + fileNameToUpload
    );

    await formalDocumentUpdatePage.save();
    expect(await formalDocumentUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await formalDocumentComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last FormalDocument', async () => {
    const nbButtonsBeforeDelete = await formalDocumentComponentsPage.countDeleteButtons();
    await formalDocumentComponentsPage.clickOnLastDeleteButton();

    formalDocumentDeleteDialog = new FormalDocumentDeleteDialog();
    expect(await formalDocumentDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Formal Document?');
    await formalDocumentDeleteDialog.clickOnConfirmButton();

    expect(await formalDocumentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
