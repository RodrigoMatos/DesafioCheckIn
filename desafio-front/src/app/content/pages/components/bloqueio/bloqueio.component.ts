import { AfterViewInit, Component, ElementRef, Input, OnDestroy, ViewChild } from '@angular/core';
import { EventEmitterService } from 'src/app/core/base/event-emitter-service';
import { ConstraintsApp } from 'src/app/core/commons/constraints-app';

@Component({
    selector: 'app-bloqueio',
    styleUrls: ['./bloqueio.component.css'],
    template: `
        <div #bloqueio class="app-bloqueio" [ngStyle]="{display: _bloqueado ? 'block' : 'none'}">
            <div class="app-bloqueio-recipiente"></div>
            <div class="app-carregando app-carregando-esquerdo"></div>
            <div class="app-carregando app-carregando-direito"></div>
        </div>
    `
})
export class BloqueioComponent implements AfterViewInit, OnDestroy {

    @Input() recipiente: HTMLElement;

    @ViewChild('bloqueio') bloqueio: ElementRef;

    _bloqueado: boolean;

    constructor(private elemento: ElementRef) {
        this._bloqueado = false;
        EventEmitterService.get(ConstraintsApp.EVENT_NAME_BLOCK).subscribe(data => {
            if (data) {
                this.bloquear();
            } else {
                this.desbloquear();
            }
        });
    }

    ngAfterViewInit() {
        if (!(this.recipiente instanceof HTMLElement)) {
            throw new Error('Target of BlockUI must implement BloquearComponent interface');
        }
    }

    ngOnDestroy() {
        this.desbloquear();
    }

    private bloquear(): void {
        if (this.recipiente) {
            this.recipiente.appendChild(this.bloqueio.nativeElement);
            this._bloqueado = true;
        }
    }

    private desbloquear(): void {
        this.elemento.nativeElement.appendChild(this.bloqueio.nativeElement);
        this._bloqueado = false;
    }

}
