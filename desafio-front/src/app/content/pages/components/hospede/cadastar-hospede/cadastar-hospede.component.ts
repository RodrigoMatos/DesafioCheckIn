import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastyService } from 'ng2-toasty';
import { BaseComponent } from 'src/app/core/base/base-component';
import { HospedeService } from 'src/app/core/service/hospede.service';
import { Hospede } from 'src/app/core/model/hospede';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-cadastar-hospede',
  templateUrl: './cadastar-hospede.component.html',
  styleUrls: ['./cadastar-hospede.component.css']
})
export class CadastarHospedeComponent extends BaseComponent implements OnInit {

  private mask9Digits = '(00) 00000-0000';
  private maskDefaultDigits = '(00) 0000-0000';
  public maskPhone: string;

  public hospede: Hospede;
  public formGroupGeral: FormGroup;

  constructor(private router: Router,
    private toasty: ToastyService, private hospedeService: HospedeService) {
    super(toasty);
  }

  ngOnInit() {
    this.maskPhone = this.maskDefaultDigits;
    this.hospede = new Hospede();
    this.initFormGroup();
  }

  private initFormGroup() {
    this.formGroupGeral = new FormGroup({
      nome: new FormControl({ value: this.hospede.nome }, [Validators.required, Validators.maxLength(50)]),
      documento: new FormControl({ value: this.hospede.documento }, [Validators.required, Validators.maxLength(15)]),
      telefone: new FormControl({ value: this.hospede.telefone },
        [Validators.required, Validators.minLength(10), Validators.maxLength(11)]),
    });
  }

  public onKeydownPhone(event) {
    if (event.target && !this.isNullOrUndefinedOrEmpty(event.target.value)) {
      if (event.target.value.length > 13) {
        this.maskPhone = this.mask9Digits;
      } else {
        this.maskPhone = this.maskDefaultDigits;
      }
    }
  }

  public salvar() {
    if (this.formGroupGeral.invalid) {
      return;
    }
    this.hospedeService.save(this.hospede).subscribe((data) => {
      this.hospede = data;
      this.irParaConsultar('Registro incluído com sucesso');
    }, (error) => {
      this.handlingError(error);
    });
  }

  private irParaConsultar(data?: string) {
    if (data) {
      this.router.navigateByData({ url: ['hospede'], data: { mensagem: data } });
    } else {
      this.router.navigate(['/hospede']);
    }
  }

}
