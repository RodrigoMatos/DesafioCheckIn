import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatPaginatorIntl, MatTooltipModule } from '@angular/material';
import { MatPaginatorI18n } from 'src/app/core/commons/mat-paginator-i18n';
import { CoreModule } from 'src/app/core/core.module';
import { MaterialModule } from 'src/app/material.module';
import { BloqueioModule } from './components/bloqueio/bloqueio.module';
import { ErroPageComponent } from './error-page/erro-page.component';
import { HeaderComponent } from './layout/header/header.component';
import { PagesRoutingModule } from './pages-routing.module';
import { PagesComponent } from './pages.component';

@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule,
    PagesRoutingModule,
    BloqueioModule,
    MaterialModule,
    MatTooltipModule,
    CoreModule
  ],
  declarations: [PagesComponent, HeaderComponent, ErroPageComponent],
  providers: [
    { provide: MatPaginatorIntl, useClass: MatPaginatorI18n }
  ],
  exports: []
})
export class PagesModule { }
