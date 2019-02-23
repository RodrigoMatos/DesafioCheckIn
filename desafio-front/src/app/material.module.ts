import { NgModule } from '@angular/core';
import { MatButtonModule, MatIconModule, MatOptionModule, MatNativeDateModule } from '@angular/material';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatRadioModule } from '@angular/material/radio';
import { MatInputModule } from '@angular/material/input';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatTableModule } from '@angular/material/table';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatDividerModule } from '@angular/material/divider';
import { MatToolbarModule } from '@angular/material/toolbar';

@NgModule({
    imports: [MatButtonModule, MatSelectModule, MatDatepickerModule, MatRadioModule, MatIconModule,
        MatButtonToggleModule, MatInputModule, MatCheckboxModule, MatOptionModule,
        MatFormFieldModule, MatExpansionModule, MatTableModule, MatDialogModule, MatSlideToggleModule, MatDividerModule, MatToolbarModule],
    exports: [MatButtonModule, MatSelectModule, MatNativeDateModule, MatDatepickerModule, MatRadioModule, MatIconModule,
        MatButtonToggleModule, MatOptionModule, MatNativeDateModule, MatInputModule, MatCheckboxModule, MatFormFieldModule,
        MatExpansionModule, MatTableModule, MatDialogModule, MatSlideToggleModule, MatDividerModule, MatToolbarModule],
})
export class MaterialModule { }
