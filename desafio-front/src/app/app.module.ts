import { HashLocationStrategy, LocationStrategy, registerLocaleData } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import localePtBr from '@angular/common/locales/pt';
import { LOCALE_ID, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatIconModule, MatTooltipModule } from '@angular/material';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import 'angular2-navigate-with-data';
import { MyDatePickerModule } from 'mydatepicker';
import { ToastyModule } from 'ng2-toasty';
import { NgxMaskModule } from 'ngx-mask';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BloqueioModule } from './content/pages/components/bloqueio/bloqueio.module';
import { CoreModule } from './core/core.module';
import { MaterialModule } from './material.module';

registerLocaleData(localePtBr);

@NgModule({
    declarations: [
        AppComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        NgxMaskModule.forRoot(),
        HttpClientModule,
        BrowserAnimationsModule,
        MaterialModule,
        MyDatePickerModule,
        ToastyModule.forRoot(),
        FormsModule,
        ReactiveFormsModule,
        MatTooltipModule,
        BloqueioModule,
        MatIconModule,
        CoreModule
    ],
    bootstrap: [AppComponent],
    providers: [
        { provide: LOCALE_ID, useValue: 'pt-BR' },
        { provide: LocationStrategy, useClass: HashLocationStrategy }
    ],
    exports: [ToastyModule]
})
export class AppModule {
}
