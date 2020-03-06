import { Injectable } from '@angular/core';
import { QuestionBase, TextBoxQuestion } from 'app/bespoke/questionnaire/question-base.model';

@Injectable({
  providedIn: 'root'
})
export class SummaryQueryModalQuestionServiceService {
  /**
   * Returns the fields to be queried
   *
   * @returns {QuestionBase<any>[]}
   */
  static getQuestions(): QuestionBase<any>[] {
    const questions: QuestionBase<any>[] = [
      new TextBoxQuestion({
        key: 'monthOfStudy',
        label: '',
        type: 'date',
        fieldType: 'input',
        order: 1
      })
    ];
    return questions.sort((a, b) => a.order - b.order);
  }
}
