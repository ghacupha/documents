import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  UserProfileComponentsPage,
  /* UserProfileDeleteDialog, */
  UserProfileUpdatePage
} from './user-profile.page-object';

const expect = chai.expect;

describe('UserProfile e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let userProfileComponentsPage: UserProfileComponentsPage;
  let userProfileUpdatePage: UserProfileUpdatePage;
  /* let userProfileDeleteDialog: UserProfileDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load UserProfiles', async () => {
    await navBarPage.goToEntity('user-profile');
    userProfileComponentsPage = new UserProfileComponentsPage();
    await browser.wait(ec.visibilityOf(userProfileComponentsPage.title), 5000);
    expect(await userProfileComponentsPage.getTitle()).to.eq('User Profiles');
    await browser.wait(
      ec.or(ec.visibilityOf(userProfileComponentsPage.entities), ec.visibilityOf(userProfileComponentsPage.noResult)),
      1000
    );
  });

  it('should load create UserProfile page', async () => {
    await userProfileComponentsPage.clickOnCreateButton();
    userProfileUpdatePage = new UserProfileUpdatePage();
    expect(await userProfileUpdatePage.getPageTitle()).to.eq('Create or edit a User Profile');
    await userProfileUpdatePage.cancel();
  });

  /* it('should create and save UserProfiles', async () => {
        const nbButtonsBeforeCreate = await userProfileComponentsPage.countDeleteButtons();

        await userProfileComponentsPage.clickOnCreateButton();

        await promise.all([
            userProfileUpdatePage.setStaffNumberInput('staffNumber'),
            userProfileUpdatePage.userSelectLastOption(),
            userProfileUpdatePage.departmentSelectLastOption(),
            // userProfileUpdatePage.transactionDocumentsSelectLastOption(),
            // userProfileUpdatePage.formalDocumentsSelectLastOption(),
        ]);

        expect(await userProfileUpdatePage.getStaffNumberInput()).to.eq('staffNumber', 'Expected StaffNumber value to be equals to staffNumber');

        await userProfileUpdatePage.save();
        expect(await userProfileUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await userProfileComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last UserProfile', async () => {
        const nbButtonsBeforeDelete = await userProfileComponentsPage.countDeleteButtons();
        await userProfileComponentsPage.clickOnLastDeleteButton();

        userProfileDeleteDialog = new UserProfileDeleteDialog();
        expect(await userProfileDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this User Profile?');
        await userProfileDeleteDialog.clickOnConfirmButton();

        expect(await userProfileComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
