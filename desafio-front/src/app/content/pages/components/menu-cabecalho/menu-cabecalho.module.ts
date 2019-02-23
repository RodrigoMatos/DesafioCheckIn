import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatIconModule, MatMenuModule } from '@angular/material';
import { MaterialModule } from 'src/app/material.module';
import { MenuCabecalhoComponent } from './menu-cabecalho.component';

@NgModule({
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    MatMenuModule,
    MaterialModule,
    MatMenuModule,
    MatIconModule
  ],
  declarations: [MenuCabecalhoComponent],
  exports: [MenuCabecalhoComponent]
})
export class MenuCabecalhoModule { }
