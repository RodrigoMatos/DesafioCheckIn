import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastyService } from 'ng2-toasty';
import { BaseComponent } from 'src/app/core/base/base-component';
import { CheckInFilter } from 'src/app/core/model/filter/checkin-filter';
import { PageDataTableCustom } from 'src/app/core/model/commons/PageDataTableCustom';
import { CheckInService } from 'src/app/core/service/checkin.service';
import { PageEvent } from '@angular/material';
import { isNullOrUndefined } from 'util';

@Component({
  selector: 'app-checkin',
  templateUrl: './checkin.component.html',
  styleUrls: ['./checkin.component.css']
})
export class CheckInComponent extends BaseComponent implements OnInit, AfterViewInit {

  private mask9Digits = '(00) 00000-0000';
  private maskDefaultDigits = '(00) 0000-0000';
  public maskPhone: string;

  public filter: CheckInFilter;

  public filterManter: CheckInFilter;

  public dataTableCustom: PageDataTableCustom;

  public pesquisou = false;
  public temRegistro = false;

  public displayedColumns: string[] = ['nome', 'documento', 'valor'];

  constructor(private router: Router,
    private toastyService: ToastyService,
    private checkInService: CheckInService) {
    super(toastyService);
  }

  ngOnInit() {
    this.maskPhone = this.maskDefaultDigits;
    this.initFilter();
  }

  ngAfterViewInit() {
    setTimeout(() => {
      this.verificarMensagens();
    });
  }

  private initFilter() {
    this.filter = new CheckInFilter();
  }

  public limpar() {
    this.initFilter();
  }

  public pesquisar() {
    this.pesquisou = true;
    this.filterManter = this.cloneObject(this.filter);
    this.refreshGrid();
  }

  public refreshGrid() {
    this.checkInService.findByFilterLazy(this.filterManter).subscribe((data) => {
      this.dataTableCustom = data;
      if (this.dataTableCustom && this.dataTableCustom.length > 0) {
        this.temRegistro = true;
      } else {
        this.temRegistro = false;
      }
    }, (error) => {
      this.handlingError(error);
    });
  }

  public getServerData(event?: PageEvent) {
    if (!isNullOrUndefined(event)) {
      this.filterManter.length = event.length;
      this.filterManter.pageIndex = event.pageIndex;
      this.filterManter.pageSize = event.pageSize;
      this.refreshGrid();
    }
  }

  public getPageSizeOptions(length: number): number[] {
    if (length <= 5) {
      return [5];
    } else if (length <= 15) {
      return [5, length];
    } else if (length <= 30) {
      return [5, 15, length];
    }
    return [5, 15, 30];
  }

  private verificarMensagens() {
    const projCurr = this.router.getNavigatedData();
    if (projCurr && projCurr['mensagem']) {
      this.mensagemSucesso(projCurr['mensagem']);
    }
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

}
