import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastyService } from 'ng2-toasty';
import { BaseComponent } from 'src/app/core/base/base-component';
import { CheckInService } from 'src/app/core/service/checkin.service';
import { Hospede } from 'src/app/core/model/hospede';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CheckIn } from 'src/app/core/model/checkin';

@Component({
  selector: 'app-cadastrar-checkin',
  templateUrl: './cadastrar-checkin.component.html',
  styleUrls: ['./cadastrar-checkin.component.css']
})
export class CadastrarCheckinComponent extends BaseComponent implements OnInit {

  public checkIn: CheckIn;

  public listHospedes: Array<Hospede>;

  public formGroupGeral: FormGroup;

  constructor(private router: Router, private route: ActivatedRoute,
    private toasty: ToastyService, private checkInService: CheckInService) {
    super(toasty);
  }

  ngOnInit() {
    this.route.data.subscribe((resolvers) => {
      this.listHospedes = resolvers.hospedes;
    }, (error) => { this.handlingError(error); });
    this.initCheckIn();
    this.initFormGroup();
  }

  private initFormGroup() {
    this.formGroupGeral = new FormGroup({
      hospede: new FormControl({ value: this.checkIn.hospede }, [Validators.required]),
      dataInicio: new FormControl({ value: this.checkIn.dataInicio }, [Validators.required]),
      dataFim: new FormControl({ value: this.checkIn.dataFim }, [Validators.required]),
      adicinalVeiculo: new FormControl({ value: this.checkIn.adicinalVeiculo }, Validators.required)
    });
  }

  private initCheckIn() {
    this.checkIn = new CheckIn();
    this.checkIn.adicinalVeiculo = false;
  }

  public salvar() {
    if (this.formGroupGeral.invalid) {
      return;
    }
    this.checkInService.save(this.checkIn).subscribe((data) => {
      this.checkIn = data;
      this.irParaConsultar('Registro incluído com sucesso');
    }, (error) => {
      this.handlingError(error);
    });
  }

  private irParaConsultar(data?: string) {
    if (data) {
      this.router.navigateByData({ url: ['checkin'], data: { mensagem: data } });
    } else {
      this.router.navigate(['/checkin']);
    }
  }

}
