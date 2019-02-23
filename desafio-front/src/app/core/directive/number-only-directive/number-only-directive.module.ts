import { NumberOnlyDirective } from './number-only-directive';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [NumberOnlyDirective],
  exports: [NumberOnlyDirective]
})
export class NumberOnlyDirectiveModule { }
