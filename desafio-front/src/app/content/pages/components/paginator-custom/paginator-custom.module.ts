import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatTooltipModule } from '@angular/material';
import { MaterialModule } from 'src/app/material.module';
import { PaginatorCustomComponent } from './paginator-custom.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    MatTooltipModule
  ],
  declarations: [PaginatorCustomComponent],
  exports: [PaginatorCustomComponent],
})
export class PaginatorCustomModule { }
