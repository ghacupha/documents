import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SchemeComponentsPage, SchemeDeleteDialog, SchemeUpdatePage } from './scheme.page-object';

const expect = chai.expect;

describe('Scheme e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let schemeComponentsPage: SchemeComponentsPage;
  let schemeUpdatePage: SchemeUpdatePage;
  let schemeDeleteDialog: SchemeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Schemes', async () => {
    await navBarPage.goToEntity('scheme');
    schemeComponentsPage = new SchemeComponentsPage();
    await browser.wait(ec.visibilityOf(schemeComponentsPage.title), 5000);
    expect(await schemeComponentsPage.getTitle()).to.eq('Schemes');
    await browser.wait(ec.or(ec.visibilityOf(schemeComponentsPage.entities), ec.visibilityOf(schemeComponentsPage.noResult)), 1000);
  });

  it('should load create Scheme page', async () => {
    await schemeComponentsPage.clickOnCreateButton();
    schemeUpdatePage = new SchemeUpdatePage();
    expect(await schemeUpdatePage.getPageTitle()).to.eq('Create or edit a Scheme');
    await schemeUpdatePage.cancel();
  });

  it('should create and save Schemes', async () => {
    const nbButtonsBeforeCreate = await schemeComponentsPage.countDeleteButtons();

    await schemeComponentsPage.clickOnCreateButton();

    await promise.all([
      schemeUpdatePage.setSchemeNameInput('schemeName'),
      schemeUpdatePage.setSchemeCodeInput('schemeCode'),
      schemeUpdatePage.setSchemeDescriptionInput('schemeDescription')
    ]);

    expect(await schemeUpdatePage.getSchemeNameInput()).to.eq('schemeName', 'Expected SchemeName value to be equals to schemeName');
    expect(await schemeUpdatePage.getSchemeCodeInput()).to.eq('schemeCode', 'Expected SchemeCode value to be equals to schemeCode');
    expect(await schemeUpdatePage.getSchemeDescriptionInput()).to.eq(
      'schemeDescription',
      'Expected SchemeDescription value to be equals to schemeDescription'
    );

    await schemeUpdatePage.save();
    expect(await schemeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await schemeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Scheme', async () => {
    const nbButtonsBeforeDelete = await schemeComponentsPage.countDeleteButtons();
    await schemeComponentsPage.clickOnLastDeleteButton();

    schemeDeleteDialog = new SchemeDeleteDialog();
    expect(await schemeDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Scheme?');
    await schemeDeleteDialog.clickOnConfirmButton();

    expect(await schemeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
