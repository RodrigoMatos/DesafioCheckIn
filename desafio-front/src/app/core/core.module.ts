import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { CNPJPipe } from 'src/app/core/pipes/cnpj-pipe';
import { TelefonePipe } from 'src/app/core/pipes/telefone-pipe';
import { DynamicPipe } from './pipes/dynamic-pipe';

@NgModule({
    imports: [
        CommonModule
    ],
    declarations: [CNPJPipe, DynamicPipe, TelefonePipe],
    exports: [CNPJPipe, DynamicPipe, TelefonePipe],
    providers: []
})
export class CoreModule { }
