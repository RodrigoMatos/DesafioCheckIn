import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { PageDataTableCustom } from 'src/app/core/model/commons/PageDataTableCustom';
import { PaginatorCustom } from 'src/app/core/model/commons/PaginatorCustom';

@Component({
  selector: 'app-paginator-custom',
  templateUrl: './paginator-custom.component.html',
  styleUrls: ['./paginator-custom.component.css']
})
export class PaginatorCustomComponent implements OnInit {

  @Input() pageDataTable: PageDataTableCustom;

  @Input() pageFilter: PaginatorCustom;

  @Output() public refresh = new EventEmitter();

  public tooltipAnterior = 'Página Anterior';
  public tooltipProximo = 'Próxima Página';

  constructor() { }

  ngOnInit() {
  }

  private runRefresh(): void {
    this.refresh.emit(null);
  }

  public backPage() {
    this.pageFilter.pageIndex = this.pageFilter.pageIndex - 1;
    this.runRefresh();
  }

  public nextPage() {
    this.pageFilter.pageIndex = this.pageFilter.pageIndex + 1;
    this.runRefresh();
  }

  public changeSizePage() {
    this.pageFilter.pageIndex = 0;
    this.runRefresh();
  }

  public getPages(): Array<Number> {
    if (this.pageDataTable.length <= 5) {
      return [5];
    } else if (this.pageDataTable.length <= 15) {
      return [5, this.pageDataTable.length];
    } else if (this.pageDataTable.length <= 30) {
      return [5, 15, this.pageDataTable.length];
    }
    return [5, 15, 30];
  }

  public isBotaoEsquerdaAtivo(): boolean {
    return !(this.pageDataTable.pageIndex === 0);
  }

  public isBotaoDireitaAtivo(): boolean {
    return !((this.pageDataTable.pageIndex * this.pageDataTable.pageSize)
      + this.pageDataTable.listObject.length === this.pageDataTable.length);
  }

  public getIndexTableFormatted(): string {
    return ((this.pageDataTable.pageIndex * this.pageDataTable.pageSize) + 1) + ' - '
      + ((this.pageDataTable.pageIndex * this.pageDataTable.pageSize) + this.pageDataTable.listObject.length)
      + ' de ' + this.pageDataTable.length;
  }

}
