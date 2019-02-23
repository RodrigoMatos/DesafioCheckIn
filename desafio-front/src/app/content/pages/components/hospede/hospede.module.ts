import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatIconModule, MatListModule, MatTooltipModule, MatPaginatorModule } from '@angular/material';
import { RouterModule } from '@angular/router';
import { ToastyModule, ToastyService } from 'ng2-toasty';
import { MaterialModule } from 'src/app/material.module';
import { CadastarHospedeComponent } from './cadastar-hospede/cadastar-hospede.component';
import { HospedeComponent } from './hospede.component';
import { MenuCabecalhoModule } from '../menu-cabecalho/menu-cabecalho.module';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BloqueioModule } from '../bloqueio/bloqueio.module';
import { NgxMaskModule } from 'ngx-mask';
import { HospedeService } from 'src/app/core/service/hospede.service';
import { CoreModule } from 'src/app/core/core.module';

@NgModule({
  imports: [
    CommonModule,
    FlexLayoutModule,
    MenuCabecalhoModule,
    MatIconModule,
    MatListModule,
    MatTooltipModule,
    MatPaginatorModule,
    MaterialModule,
    HttpClientModule,
    FormsModule,
    BloqueioModule,
    CoreModule,
    NgxMaskModule.forRoot(),
    ToastyModule.forRoot(),
    ReactiveFormsModule.withConfig({ warnOnNgModelWithFormControl: 'never' }),
    RouterModule.forChild([
      {
        path: '',
        component: HospedeComponent
      },
      {
        path: 'cadastrar',
        component: CadastarHospedeComponent
      }
    ]),
  ],
  declarations: [HospedeComponent, CadastarHospedeComponent],
  providers: [HospedeService, ToastyService],
  exports: [ToastyModule]
})
export class HospedeModule { }
