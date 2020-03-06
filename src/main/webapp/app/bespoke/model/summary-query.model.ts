import { Moment } from 'moment';
import * as moment from 'moment';

export interface ISummaryQuery {
  monthOfStudy?: Moment;
}
export class SummaryQuery implements ISummaryQuery {
  private _monthOfStudy!: Moment;

  constructor(
    public parameters: {
      monthOfStudy?: Moment;
    }
  ) {
    const defaultDate: Moment = moment('2018-06-01', 'YYYY-MM-DD');
    this._monthOfStudy = parameters.monthOfStudy || defaultDate;
  }

  get monthOfStudy(): moment.Moment {
    return this._monthOfStudy;
  }

  set monthOfStudy(value: moment.Moment) {
    this._monthOfStudy = value;
  }

  public toString(): string {
    return this._monthOfStudy.format('YYYY-MM-DD');
  }
}
