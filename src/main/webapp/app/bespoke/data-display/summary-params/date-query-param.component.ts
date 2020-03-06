import { Component, OnDestroy, OnInit } from '@angular/core';
import { NGXLogger } from 'ngx-logger';
import { NavigationExtras, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RouteStateService } from 'app/bespoke/route-state.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { QuestionBase } from 'app/bespoke/questionnaire/question-base.model';
import { SummaryQueryModalQuestionServiceService } from 'app/bespoke/data-display/summary-params/summary-query-modal-question-service.service';
import * as moment from 'moment';
import { ISummaryQuery, SummaryQuery } from 'app/bespoke/model/summary-query.model';

/**
 * Am adding a new feature that looks into the type of navigation that should be accomplished
 * for different table type.
 * I have other alternatives such as creating multiple instances of this component and making the
 * main functionality except the navigation path as abstract as possible.
 * For now am settling for setting the navigation path in the initialization callback: onInit
 */
@Component({
  selector: 'gha-date-query-param',
  templateUrl: './date-query-param.component.html',
  styleUrls: ['./date-query-param.component.scss']
})
export class DateQueryParamComponent implements OnInit, OnDestroy {
  questions!: QuestionBase<any>[];
  summaryQuery!: ISummaryQuery;
  isSaving!: boolean;
  navigationPath!: string;

  editForm = this.fb.group({
    balanceDate: [null, [Validators.required]],
    accountName: [],
    serviceOutlet: []
  });

  constructor(
    private fb: FormBuilder,
    private log: NGXLogger,
    private router: Router,
    private stateService: RouteStateService<ISummaryQuery>,
    private statePathDataService: RouteStateService<string>,
    // close modal if data entered has error
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.isSaving = false;
    this.summaryQuery = {};
    this.questions = SummaryQueryModalQuestionServiceService.getQuestions();
    this.navigationPath = this.statePathDataService.data;
    this.log.debug(`Entering query for summaries ... Standby for navigation to ${this.navigationPath}`);
    this.statePathDataService.reset();
  }

  ngOnDestroy(): void {
    this.questions = [];
  }

  updateQuery(queryForm: FormGroup): void {
    this.summaryQuery = this.createFromForm(queryForm);
    this.log.debug(`Report date updated to : ${this.summaryQuery}`);

    this.enquire(this.summaryQuery);
  }

  enquire(summaryDateQuery: ISummaryQuery): void {
    this.log.debug(`Navigating to report for date : ${summaryDateQuery};`);

    this.stateService.data = summaryDateQuery;
    // TODO handle input errors
    const navigationExtras: NavigationExtras = {
      queryParams: {
        monthOfStudy: summaryDateQuery.monthOfStudy!.format('YYYY-MM-DD')
      }
    };

    const navigation = this.router.navigate([this.navigationPath], navigationExtras);

    this.reviewNavigation(navigation, summaryDateQuery);
  }

  private createFromForm(queryForm: FormGroup): ISummaryQuery {
    return new SummaryQuery({
      monthOfStudy: moment(queryForm.get(['monthOfStudy'])!.value, 'YYYY-MM-DD')
    });
  }

  private reviewNavigation(navigation: any, summaryDateQuery: ISummaryQuery): void {
    // note success
    navigation.then(() => {
      this.log.debug(`Navigation to '${summaryDateQuery} completed successfully`);
      this.modalService.dismissAll('Data table navigation complete!!!');
    });

    // catch unfortunate navigation incidences
    navigation.catch(() => {
      this.log.debug(`Navigation to '${summaryDateQuery} failed navigating back to previous view...`);
      this.previousState();
    });

    // clean up mmodals
    navigation.finally(() => {
      this.log.debug(`Navigation to '${summaryDateQuery} completed successfully`);
      this.modalService.dismissAll('In case dismissal has failed');
    });
  }

  previousState(): void {
    window.history.back();
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }
}
