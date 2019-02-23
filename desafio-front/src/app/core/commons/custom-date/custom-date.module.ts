import { ReactiveFormsModule } from '@angular/forms';
import { ToastyModule } from 'ng2-toasty';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { NgxMaskModule } from 'ngx-mask';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomDateComponent } from './custom-date/custom-date.component';
import { CustomDateReferenceComponent } from './custom-date-reference/custom-date-reference.component';
import { MaterialModule } from 'src/app/material.module';

@NgModule({
  imports: [
    CommonModule,
    NgxMaskModule.forRoot(),
    HttpClientModule,
    MaterialModule,
    ToastyModule.forRoot(),
    ReactiveFormsModule
  ],
  declarations: [CustomDateComponent, CustomDateReferenceComponent],
  exports: [CustomDateComponent, CustomDateReferenceComponent]
})
export class CustomDateModule { }
