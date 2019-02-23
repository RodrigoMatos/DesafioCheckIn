import { Directive, ElementRef, HostListener, Input } from '@angular/core';
import { isNullOrUndefined } from 'util';

@Directive({
    selector: '[appMyNumberOnly]'
})
export class NumberOnlyDirective {

    // Allow decimal numbers and negative values
    private regex: RegExp = new RegExp(/^-?[0-9]+(\.[0-9]*){0,1}$/g);
    // Allow key codes for special events. Reflect :
    // Backspace, tab, end, home
    private specialKeys: Array<string> = ['Backspace', 'Delete', 'Tab', 'End', 'Home', '-',
        'ArrowRight', 'ArrowLeft', 'ArrowUp', 'ArrowDown', 'F1', 'F5', 'F12'];

    @Input() maxDecimal: number;
    @Input() maxInteiro: number;
    @Input() onlyPositive: boolean;

    constructor(private el: ElementRef) {
    }

    @HostListener('paste', ['$event'])
    onPaste(event) {
        if (!isNullOrUndefined(event)) {
            const clipboardData = event.clipboardData;
            if (!isNullOrUndefined(clipboardData)) {
                const textPaste = clipboardData.getData('text');
                if (textPaste.indexOf('-') !== -1 && this.onlyPositive) {
                    event.preventDefault();
                    return;
                }
                this.validateContent(event, textPaste);
            }
        }
    }

    @HostListener('keydown', ['$event'])
    onKeyDown(event: KeyboardEvent) {

        if (event.ctrlKey && (event.key === 'v' || event.key === 'c' || event.key === 'z' ||
            event.key === 'a' || event.key === 'x')) {
            return;
        }

        if (isNullOrUndefined(this.maxDecimal) &&
            isNullOrUndefined(this.maxInteiro) && this.onlyPositive) {
            // CASO QUE É SÓ PARA ACEITAR NUMEROS POSSITIVOS SEM LIMITE
            if (event.key === '-' || ((!String(event.key).match(this.regex)) && !(this.specialKeys.indexOf(event.key) !== -1))) {
                event.preventDefault();
            }
            return;
        }

        if (event.key === '.' && (isNullOrUndefined(this.maxDecimal) || this.maxDecimal == 0)) {
            event.preventDefault();
            return;
        }
        this.validateContent(event, event.key);
    }

    private validateContent(event, value: string) {

        const current: string = this.el.nativeElement.value;
        const next: string = current.concat(value);
        const charNegativo = current.indexOf('-') !== -1;

        if (value === '-') {
            if (charNegativo || this.onlyPositive) {
                event.preventDefault();
                return;
            }
        }

        // Allow Backspace, tab, end, and home keys
        if (this.specialKeys.indexOf(value) !== -1) {
            return;
        }

        let validarTamanho = false;
        if (this.maxDecimal !== undefined) {
            validarTamanho = true;
        }
        let inteiroInput = 0;
        let decimalInput = 0;
        if (validarTamanho) {
            if (next.indexOf('.') !== -1) {
                inteiroInput = next.split('.')[0].length;
                decimalInput = next.split('.')[1].length;
            } else {
                inteiroInput = next.length;
            }
        }
        if (charNegativo) {
            inteiroInput = inteiroInput - 1;
        }
        if (next && (!String(next).match(this.regex) ||
            (validarTamanho && (this.maxInteiro < inteiroInput
                || this.maxDecimal < decimalInput)))) {
            event.preventDefault();
            return;
        }
    }

}
