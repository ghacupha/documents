import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  TransactionDocumentComponentsPage,
  TransactionDocumentDeleteDialog,
  TransactionDocumentUpdatePage
} from './transaction-document.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('TransactionDocument e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let transactionDocumentComponentsPage: TransactionDocumentComponentsPage;
  let transactionDocumentUpdatePage: TransactionDocumentUpdatePage;
  let transactionDocumentDeleteDialog: TransactionDocumentDeleteDialog;
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

  it('should load TransactionDocuments', async () => {
    await navBarPage.goToEntity('transaction-document');
    transactionDocumentComponentsPage = new TransactionDocumentComponentsPage();
    await browser.wait(ec.visibilityOf(transactionDocumentComponentsPage.title), 5000);
    expect(await transactionDocumentComponentsPage.getTitle()).to.eq('Transaction Documents');
    await browser.wait(
      ec.or(ec.visibilityOf(transactionDocumentComponentsPage.entities), ec.visibilityOf(transactionDocumentComponentsPage.noResult)),
      1000
    );
  });

  it('should load create TransactionDocument page', async () => {
    await transactionDocumentComponentsPage.clickOnCreateButton();
    transactionDocumentUpdatePage = new TransactionDocumentUpdatePage();
    expect(await transactionDocumentUpdatePage.getPageTitle()).to.eq('Create or edit a Transaction Document');
    await transactionDocumentUpdatePage.cancel();
  });

  it('should create and save TransactionDocuments', async () => {
    const nbButtonsBeforeCreate = await transactionDocumentComponentsPage.countDeleteButtons();

    await transactionDocumentComponentsPage.clickOnCreateButton();

    await promise.all([
      transactionDocumentUpdatePage.setTransactionNumberInput('transactionNumber'),
      transactionDocumentUpdatePage.setTransactionDateInput('2000-12-31'),
      transactionDocumentUpdatePage.setBriefDescriptionInput('briefDescription'),
      transactionDocumentUpdatePage.setJustificationInput('justification'),
      transactionDocumentUpdatePage.setTransactionAmountInput('5'),
      transactionDocumentUpdatePage.setPayeeNameInput('payeeName'),
      transactionDocumentUpdatePage.setInvoiceNumberInput('invoiceNumber'),
      transactionDocumentUpdatePage.setLpoNumberInput('lpoNumber'),
      transactionDocumentUpdatePage.setDebitNoteNumberInput('debitNoteNumber'),
      transactionDocumentUpdatePage.setLogisticReferenceNumberInput('logisticReferenceNumber'),
      transactionDocumentUpdatePage.setMemoNumberInput('memoNumber'),
      transactionDocumentUpdatePage.setDocumentStandardNumberInput('documentStandardNumber'),
      transactionDocumentUpdatePage.setTransactionAttachmentInput(absolutePath),
      transactionDocumentUpdatePage.setFilenameInput('filename')
      // transactionDocumentUpdatePage.schemesSelectLastOption(),
    ]);

    expect(await transactionDocumentUpdatePage.getTransactionNumberInput()).to.eq(
      'transactionNumber',
      'Expected TransactionNumber value to be equals to transactionNumber'
    );
    expect(await transactionDocumentUpdatePage.getTransactionDateInput()).to.eq(
      '2000-12-31',
      'Expected transactionDate value to be equals to 2000-12-31'
    );
    expect(await transactionDocumentUpdatePage.getBriefDescriptionInput()).to.eq(
      'briefDescription',
      'Expected BriefDescription value to be equals to briefDescription'
    );
    expect(await transactionDocumentUpdatePage.getJustificationInput()).to.eq(
      'justification',
      'Expected Justification value to be equals to justification'
    );
    expect(await transactionDocumentUpdatePage.getTransactionAmountInput()).to.eq(
      '5',
      'Expected transactionAmount value to be equals to 5'
    );
    expect(await transactionDocumentUpdatePage.getPayeeNameInput()).to.eq(
      'payeeName',
      'Expected PayeeName value to be equals to payeeName'
    );
    expect(await transactionDocumentUpdatePage.getInvoiceNumberInput()).to.eq(
      'invoiceNumber',
      'Expected InvoiceNumber value to be equals to invoiceNumber'
    );
    expect(await transactionDocumentUpdatePage.getLpoNumberInput()).to.eq(
      'lpoNumber',
      'Expected LpoNumber value to be equals to lpoNumber'
    );
    expect(await transactionDocumentUpdatePage.getDebitNoteNumberInput()).to.eq(
      'debitNoteNumber',
      'Expected DebitNoteNumber value to be equals to debitNoteNumber'
    );
    expect(await transactionDocumentUpdatePage.getLogisticReferenceNumberInput()).to.eq(
      'logisticReferenceNumber',
      'Expected LogisticReferenceNumber value to be equals to logisticReferenceNumber'
    );
    expect(await transactionDocumentUpdatePage.getMemoNumberInput()).to.eq(
      'memoNumber',
      'Expected MemoNumber value to be equals to memoNumber'
    );
    expect(await transactionDocumentUpdatePage.getDocumentStandardNumberInput()).to.eq(
      'documentStandardNumber',
      'Expected DocumentStandardNumber value to be equals to documentStandardNumber'
    );
    expect(await transactionDocumentUpdatePage.getTransactionAttachmentInput()).to.endsWith(
      fileNameToUpload,
      'Expected TransactionAttachment value to be end with ' + fileNameToUpload
    );
    expect(await transactionDocumentUpdatePage.getFilenameInput()).to.eq('filename', 'Expected Filename value to be equals to filename');

    await transactionDocumentUpdatePage.save();
    expect(await transactionDocumentUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await transactionDocumentComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last TransactionDocument', async () => {
    const nbButtonsBeforeDelete = await transactionDocumentComponentsPage.countDeleteButtons();
    await transactionDocumentComponentsPage.clickOnLastDeleteButton();

    transactionDocumentDeleteDialog = new TransactionDocumentDeleteDialog();
    expect(await transactionDocumentDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Transaction Document?');
    await transactionDocumentDeleteDialog.clickOnConfirmButton();

    expect(await transactionDocumentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
