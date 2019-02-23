import { Location } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BaseComponent } from 'src/app/core/base/base-component';
import { MenuConfig } from 'src/app/core/commons/model/menu-config';

@Component({
  selector: 'app-menu-cabecalho',
  templateUrl: './menu-cabecalho.component.html',
  styleUrls: ['./menu-cabecalho.component.css']
})
export class MenuCabecalhoComponent extends BaseComponent implements OnInit {


  @Input() public titulo: string;

  @Input() public showBack = false;

  private configModel: MenuConfig = new MenuConfig();

  public listMenu: Array<any>;

  constructor(private router: Router, private _location: Location) {
    super();
  }

  ngOnInit() {
    this.listMenu = [];
    if (this.configModel.config.items) {
      this.configModel.config.items.forEach(item => {
        this.listMenu.push(item);
      });
    }
  }

  public navegarPara(url: string) {
    this.router.navigate([url]);
  }

  public back() {
    this._location.back();
  }

}
