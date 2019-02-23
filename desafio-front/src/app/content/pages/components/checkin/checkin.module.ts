import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatIconModule, MatListModule, MatTooltipModule, MatPaginatorModule } from '@angular/material';
import { RouterModule } from '@angular/router';
import { ToastyModule, ToastyService } from 'ng2-toasty';
import { MaterialModule } from 'src/app/material.module';
import { MenuCabecalhoModule } from '../menu-cabecalho/menu-cabecalho.module';
import { PaginatorCustomModule } from '../paginator-custom/paginator-custom.module';
import { CadastrarCheckinComponent } from './cadastrar-checkin/cadastrar-checkin.component';
import { CheckInComponent } from './checkin.component';
import { HospedeResolver } from 'src/app/core/resolver/hospede.resolver';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BloqueioModule } from '../bloqueio/bloqueio.module';
import { CheckInService } from 'src/app/core/service/checkin.service';
import { HospedeService } from 'src/app/core/service/hospede.service';
import { CustomDateModule } from 'src/app/core/commons/custom-date/custom-date.module';
import { NgxMaskModule } from 'ngx-mask';
import { CoreModule } from 'src/app/core/core.module';

@NgModule({
  imports: [
    CommonModule,
    MenuCabecalhoModule,
    FlexLayoutModule,
    MatIconModule,
    MatListModule,
    MatTooltipModule,
    MaterialModule,
    PaginatorCustomModule,
    MatPaginatorModule,
    HttpClientModule,
    FormsModule,
    BloqueioModule,
    CoreModule,
    CustomDateModule,
    NgxMaskModule.forRoot(),
    ToastyModule.forRoot(),
    ReactiveFormsModule.withConfig({ warnOnNgModelWithFormControl: 'never' }),
    RouterModule.forChild([
      {
        path: '',
        component: CheckInComponent
      },
      {
        path: 'cadastrar',
        component: CadastrarCheckinComponent,
        resolve: {
          hospedes: HospedeResolver
        }
      }
    ]),
  ],
  declarations: [CheckInComponent, CadastrarCheckinComponent],
  providers: [ToastyService, HospedeResolver, HospedeService, CheckInService]
})
export class CheckInModule { }
