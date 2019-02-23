import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MomentDateAdapter } from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { MatDatepicker } from '@angular/material/datepicker';
// Depending on whether rollup is used, moment needs to be imported differently.
// Since Moment.js doesn't have a default export, we normally need to import using the `* as`
// syntax. However, rollup creates a synthetic default module and we thus need to import it using
// the `default as` syntax.
import * as _moment from 'moment';
// tslint:disable-next-line:no-duplicate-imports
import { Moment } from 'moment';
import { GenericCustomDate } from '../generic-custom-date';
import { DateUtil } from 'src/utils/date.utils';

const moment = _moment;

export const MY_FORMATS = {
  parse: {
    dateInput: 'MM/YYYY',
  },
  display: {
    dateInput: 'MM/YYYY',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY',
  },
};

@Component({
  selector: 'app-custom-date-reference',
  templateUrl: './custom-date-reference.component.html',
  styleUrls: ['./custom-date-reference.component.css'],
  providers: [
    { provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE] },
    { provide: MAT_DATE_FORMATS, useValue: MY_FORMATS },
  ],
})
export class CustomDateReferenceComponent extends GenericCustomDate implements OnInit {

  private dateTemp: any;

  constructor(protected cdf: ChangeDetectorRef) {
    super(cdf);
  }

  ngOnInit() {
    super.ngOnInit();
    this.load();
  }

  public onSelectYear(normalizedYear: Moment) {
    this.dateTemp = moment().date(1);
    this.dateTemp.year(moment(normalizedYear, DateUtil.brFormat).year());
  }

  public onSelectMonth(normlizedMonth: Moment, datepicker: MatDatepicker<Moment>) {
    this.dateTemp.month(moment(normlizedMonth, DateUtil.brFormat).month());
    this.fieldDate.setValue(this.dateTemp);
    this.onChangeDate();
    datepicker.close();
  }

}
