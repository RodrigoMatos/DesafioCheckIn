import { IMyOptions, IMyDate, IMyDpOptions } from 'mydatepicker';
import * as moment from 'moment';

export const myDatePickerOptions: IMyDpOptions = {
    dayLabels: { su: 'Dom', mo: 'Seg', tu: 'Ter', we: 'Qua', th: 'Qui', fr: 'Sex', sa: 'Sab' },
    monthLabels: { 1: 'Jan', 2: 'Fev', 3: 'Mar', 4: 'Abr', 5: 'Mai', 6: 'Jun', 7: 'Jul',
     8: 'Ago', 9: 'Set', 10: 'Out', 11: 'Nov', 12: 'Dez' },
    dateFormat: 'dd/mm/yyyy',
    todayBtnTxt: 'Hoje',
    firstDayOfWeek: 'su',
    sunHighlight: true,
    openSelectorOnInputClick: true,
    editableDateField: false,
    indicateInvalidDate: false,
    width: '100%',
    inline: false,
    selectionTxtFontSize: '15px',
    alignSelectorRight: false,
};

export class DateUtil {
    static brFormat = 'DD/MM/YYYY';
    static enFormat = 'YYYY-MM-DD';
    public static formatDate(date: string): string {
        const pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
        return date.replace(pattern, '$3-$2-$1');
    }

    public static formatDateTime(date: string, time: string): string {
        const pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
        return moment(date.replace(pattern, '$3-$2-$1') + ' ' + time, moment.ISO_8601).format();
    }

    public static getDateNow(): any {
        const rightNow = new Date();
        return rightNow;
    }

    public static diffDate(startDate, endDate): number {
        return moment(endDate).diff(moment(startDate));
    }

    public static objectToDate(obejctDate: IMyDate): moment.Moment {
        return moment([obejctDate.year, obejctDate.month - 1, obejctDate.day]);
    }

    public static getYearNow(): number {
        return moment().year();
    }
}
