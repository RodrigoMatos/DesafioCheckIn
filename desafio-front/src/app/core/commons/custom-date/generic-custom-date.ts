import { AfterViewInit, ChangeDetectorRef, ElementRef, HostListener, Input, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { isNullOrUndefined } from 'util';
import { DateUtil } from 'src/utils/date.utils';

export class GenericCustomDate implements OnInit, AfterViewInit {

    public fieldDate: FormControl;

    private regex: RegExp = new RegExp(/^-?([0-9]+)$/g);

    private specialKeys: Array<string> = ['Backspace', 'Delete', 'Tab', 'End', 'Home',
        'ArrowRight', 'ArrowLeft', 'ArrowUp', 'ArrowDown', 'F1', 'F5', 'F12'];

    @Input() public formGroupCustom: FormGroup;

    @Input() public validCustom = false;

    @Input() public entity: any;

    @Input() public entityField: string;

    @Input() public disabled = false;

    @Input() public id: string;

    @Input() public placeholder: string;

    // Desabilitar o autocomplete dos browsers caso false
    @Input() public autoComplete = true;

    @ViewChild('inputDate') input: ElementRef;

    constructor(protected cdf: ChangeDetectorRef) {
    }

    ngOnInit() {
        if (!isNullOrUndefined(this.id)) {
            this.id = 'input-' + this.id;
        }
        this.loadValid();
    }

    ngAfterViewInit() {
        this.loadValid();
    }

    private loadValid() {
        if (isNullOrUndefined(this.formGroupCustom)) {
            this.formGroupCustom = new FormGroup({
                input: new FormControl('')
            });
        }
        this.disabled = this.disabled || this.formGroupCustom.disabled;
    }

    protected getDisabled(): boolean {
        return this.disabled || this.formGroupCustom.disabled;
    }

    protected load() {
        if (isNullOrUndefined(this.fieldDate) && !isNullOrUndefined(this.entity) && !isNullOrUndefined(this.entityField)) {
            this.fieldDate = new FormControl();
        }
    }

    protected onChangeDate(event?) {
        if (event) {
            this.fieldDate = event;
        }
        if (!isNullOrUndefined(this.fieldDate.value)) {
            this.entity[this.entityField] = this.fieldDate.value.format(DateUtil.enFormat) + 'T03:00:00';
        }
    }

    protected showClearButton(): boolean {
        return !isNullOrUndefined(this.entity) && !isNullOrUndefined(this.entity[this.entityField]) && this.entity[this.entityField] !== '';
    }

    protected clear() {
        this.input.nativeElement.value = '';
        this.entity[this.entityField] = undefined;
        this.cdf.detectChanges();
    }

    @HostListener('keyDown', ['$event'])
    protected onKeyDown(event) {
        if (this.specialKeys.indexOf(event.key) !== -1 ||
            (event.ctrlKey && (event.key === 'v' || event.key === 'c' || event.key === 'x'
                || event.key === 'z' || event.key === 'a'))) {
            return;
        }

        if (!String(event.key).match(this.regex)) {
            event.preventDefault();
            return;
        }
        let value = (this.input.nativeElement.value + event.key).replace(new RegExp('/', 'g'), '');
        if (this.constructor.name === 'CustomDateComponent') {
            if (value.length > 8) {
                event.preventDefault();
            } else if (value.length >= 3) {
                if (value.length >= 5) {
                    value = value.substring(0, 2) + '/' + value.substring(2, 4) + '/' + value.substring(4, value.length - 1);
                } else {
                    value = value.substring(0, 2) + '/' + value.substring(2, value.length - 1);
                }
                this.input.nativeElement.value = value;
            }
        } else if (this.constructor.name === 'CustomDateReferenceComponent') {
            if (value.length > 6) {
                event.preventDefault();
            } else if (value.length >= 3) {
                value = value.substring(0, 2) + '/' + value.substring(2, value.length - 1);
                this.input.nativeElement.value = value;
            }
        }
    }

    @HostListener('blur', ['$event'])
    protected onBlur(event) {
        if (isNullOrUndefined(this.entity[this.entityField]) || this.entity[this.entityField] === '') {
            this.clear();
        } else if (typeof this.entity[this.entityField] === 'object') {
            this.fieldDate.setValue(this.entity[this.entityField]);
            this.onChangeDate();
        }
        this.cdf.detectChanges();
    }

}
